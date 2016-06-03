package com.verdant.jools.db.orm.mybatis.interceptor;


import com.verdant.jools.db.orm.mybatis.dialect.Dialect;
import com.verdant.jools.db.orm.mybatis.enums.MappingEnum;
import com.verdant.jools.db.orm.mybatis.sqlsource.CustomerProviderSqlSource;
import com.verdant.jools.db.orm.mybatis.utils.MetaObjectUtils;
import com.verdant.jools.db.orm.mybatis.utils.MybatisUtils;
import com.verdant.jools.db.orm.mybatis.constant.Const;
import com.verdant.jools.db.orm.mybatis.dialect.DialectFactory;
import com.verdant.jools.db.orm.mybatis.sqlsource.CustomerDynamicSqlSource;
import com.verdant.jtools.common.orm.model.PageParam;
import com.verdant.jtools.common.orm.model.PageWrapper;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * MyBatis分页拦截
 *
 * @author verdant
 * @version 2015年2月10日
 */
@Intercepts(@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class ExecutorQueryHandlerInterceptor implements Interceptor {
    final static Logger log = LoggerFactory.getLogger(ExecutorQueryHandlerInterceptor.class);

    Dialect dialect = null;

    public Object doPage(Invocation invocation) throws Throwable {
        log.debug("进入Executor");
        final Object[] args = invocation.getArgs();
        RowBounds rowBounds = (RowBounds) args[2];
        PageParam pageParam = null;// PageContextHolder.get();
        if (args[1] instanceof MapperMethod.ParamMap<?>) {
            MapperMethod.ParamMap<?> map = (MapperMethod.ParamMap<?>) args[1];
            for (String key : map.keySet()) {
                if (key.startsWith("param")) {
                    Object obj = map.get(key);
                    if (obj instanceof PageParam) {
                        pageParam = (PageParam) obj;
                    } else if (args[1] instanceof MapperMethod.ParamMap<?>) {
                        args[1] = obj;
                    }

                }
            }
        }
        // 无分页参数
        if (pageParam == null) {
            log.debug("默认查询");
            return invocation.proceed();
        }
        log.debug("分页查询");
        PageWrapper page = new PageWrapper();
        MappedStatement ms = (MappedStatement) args[0];
        args[2] = RowBounds.DEFAULT;
        // 无需分页
        if (pageParam.getPageSize() == 0) {
            log.debug("全量查询");
            Object result = invocation.proceed();
            page.addAll((List) result);
            page.setPageNo(1);
            page.setPageSize(page.size());
            page.setTotalPage(1);
            page.setTotalCount(page.size());
            return page;
        }
        SqlSource sqlSource = ((MappedStatement) args[0]).getSqlSource();
        dialect = DialectFactory.buildDialect(ms.getConfiguration());

        // 需总数
        args[0] = getMappedStatement(ms, sqlSource, args[1], Const.CACHE_KEY_COUNT, MappingEnum.INT);
        Object resultCount = invocation.proceed();
        int totalCount = ((Integer) ((List) resultCount).get(0));
        log.debug("总条数查询为 {}", totalCount);
        page.setTotalCount(totalCount);
        page.setPageNo(pageParam.getPageNo());
        page.setPageSize(pageParam.getPageSize());
        // 提升效率：count为0不再分页查
        if (page.getTotalCount() == 0) {
            return page;
        }

        // 需分页
        // 便捷查询：pageSize<=0 仅查询count
        if (pageParam.getPageSize() > 0) {
            args[0] = getMappedStatement(ms, sqlSource, args[1], Const.CACHE_KEY_PAGE, MappingEnum.DEFAULT);
            args[1] = setParameter((MappedStatement) args[0], args[1], pageParam, dialect);
            Object result = invocation.proceed();
            page.addAll((List) result);
            log.debug("分页查询结束 {}", pageParam);
        }
        return page;
    }

    private MappedStatement getMappedStatement(MappedStatement ms, SqlSource sqlSource, Object parameterObject,
                                               String cacheSuffixKey, MappingEnum mappingType) {
        String mappedStatementId = ms.getId() + cacheSuffixKey;
        MappedStatement qs = MybatisUtils.getMappedStatement(ms, mappedStatementId);
        if (qs == null) {
            // 创建一个新的MappedStatement
            SqlSource ss = getSqlSource(ms, sqlSource, parameterObject, cacheSuffixKey == Const.CACHE_KEY_COUNT);
            qs = MybatisUtils.createMappedStatement(ms, ss, cacheSuffixKey, mappingType);
            MybatisUtils.addMappedStatement(ms.getConfiguration(), ms);
        }
        return qs;
    }

    private SqlSource getSqlSource(MappedStatement ms, SqlSource sqlSource, Object parameterObject, boolean isCount) {
        if (ms.getSqlSource() instanceof DynamicSqlSource) {
            MetaObject msObject = MetaObjectUtils.forObject(ms);
            SqlNode sqlNode = (SqlNode) msObject.getValue("sqlSource.rootSqlNode");
            MixedSqlNode mixedSqlNode = null;
            if (sqlNode instanceof MixedSqlNode) {
                mixedSqlNode = (MixedSqlNode) sqlNode;
            } else {
                List<SqlNode> contents = new ArrayList<SqlNode>(1);
                contents.add(sqlNode);
                mixedSqlNode = new MixedSqlNode(contents);
            }
            return new CustomerDynamicSqlSource(ms.getConfiguration(), mixedSqlNode, isCount, dialect);
        } else if (sqlSource instanceof ProviderSqlSource) {
            return new CustomerProviderSqlSource(ms.getConfiguration(), (ProviderSqlSource) sqlSource, isCount, dialect);
        } else if (!isCount) {
            BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
            return new StaticSqlSource(ms.getConfiguration(), dialect.generatePageSQL(boundSql.getSql()),
                    MybatisUtils.plusTwoParameterToMapping(ms.getConfiguration(), boundSql));
        } else {
            BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
            return new StaticSqlSource(ms.getConfiguration(), dialect.generateCountSQL(boundSql.getSql()),
                    boundSql.getParameterMappings());
        }
    }

    public Map setParameter(MappedStatement ms, Object parameterObject, PageParam page, Dialect dialect) {
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        Map paramMap = null;
        if (parameterObject == null) {
            paramMap = new HashMap();
        } else if (parameterObject instanceof Map) {
            paramMap = (Map) parameterObject;
        } else {
            paramMap = new HashMap();
            boolean hasTypeHandler = ms.getConfiguration().getTypeHandlerRegistry()
                    .hasTypeHandler(parameterObject.getClass());
            MetaObject metaObject = MetaObjectUtils.forObject(parameterObject);
            if (ms.getSqlSource() instanceof CustomerProviderSqlSource) {
                paramMap.put(Const.PROVIDER_OBJECT, parameterObject);
            }
            if (!hasTypeHandler) {
                for (String name : metaObject.getGetterNames()) {
                    paramMap.put(name, metaObject.getValue(name));
                }
            }
            if (boundSql.getParameterMappings() != null && boundSql.getParameterMappings().size() > 0) {
                for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
                    String name = parameterMapping.getProperty();
                    if (!name.equals(Const.PARAMETER_FIRST) && !name.equals(Const.PARAMETER_SECOND)
                            && paramMap.get(name) == null) {
                        if (hasTypeHandler || parameterMapping.getJavaType().equals(parameterObject.getClass())) {
                            paramMap.put(name, parameterObject);
                            break;
                        }
                    }
                }
            }
        }
        // 备份原始参数对象
        paramMap.put(Const.ORIGINAL_PARAMETER_OBJECT, parameterObject);
        // 设置分页参数
        Map<String, Object> customerPageParams = dialect.getPageParameter(page);
        if (customerPageParams != null) {
            for (String key : customerPageParams.keySet()) {
                paramMap.put(key, customerPageParams.get(key));
            }
        }
        return paramMap;
    }

    public Object intercept(Invocation invocation) throws Throwable {
        return doPage(invocation);
    }

    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    public void setProperties(Properties p) {
    }
}
