package com.verdant.jools.db.orm.mybatis.sqlsource;

import com.verdant.jools.db.orm.mybatis.dialect.Dialect;
import com.verdant.jools.db.orm.mybatis.utils.MybatisUtils;
import com.verdant.jools.db.orm.mybatis.constant.Const;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;

import java.util.Map;



public class CustomerDynamicSqlSource implements SqlSource
{

    private Configuration configuration;

    private SqlNode rootSqlNode;

    private Boolean count;

    private Dialect dialect;

    public CustomerDynamicSqlSource(Configuration configuration, SqlNode rootSqlNode, Boolean count,Dialect dialect)
    {
        this.configuration = configuration;
        this.rootSqlNode = rootSqlNode;
        this.count = count;
        this.dialect = dialect;
    }

    public BoundSql getBoundSql(Object parameterObject)
    {
        DynamicContext context;
        if (parameterObject != null && parameterObject instanceof Map
                && ((Map) parameterObject).containsKey(Const.ORIGINAL_PARAMETER_OBJECT))
        {
            context = new DynamicContext(configuration, ((Map) parameterObject).get(Const.ORIGINAL_PARAMETER_OBJECT));
        }
        else
        {
            context = new DynamicContext(configuration, parameterObject);
        }
        rootSqlNode.apply(context);
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
        SqlSource sqlSource = sqlSourceParser.parse(context.getSql(), parameterType, context.getBindings());
        if (count)
        {
            BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
            sqlSource = new StaticSqlSource(configuration, dialect.generateCountSQL(boundSql.getSql()), boundSql.getParameterMappings());
        }
        else
        {
            BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
            sqlSource =  new StaticSqlSource(configuration, dialect.generatePageSQL(boundSql.getSql()), MybatisUtils.plusTwoParameterToMapping(configuration, boundSql));
        }
        BoundSql boundSql = sqlSource.getBoundSql(parameterObject);
        // 设置条件参数
        for (Map.Entry<String, Object> entry : context.getBindings().entrySet())
        {
            boundSql.setAdditionalParameter(entry.getKey(), entry.getValue());
        }
        return boundSql;
    }
}
