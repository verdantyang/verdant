/*
 * 文  件  名：MappedStatementUtils.java
 * 版         权：Copyright 2014 GSOFT Tech.Co.Ltd.All Rights Reserved.
 * 描         述：
 * 修  改  人：hadoop
 * 修改时间：2015年2月11日
 * 修改内容：新增
 */
package com.verdant.jools.db.orm.mybatis.utils;

import com.verdant.jools.db.orm.mybatis.enums.MappingEnum;
import com.verdant.jools.db.orm.mybatis.constant.Const;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * @author verdant
 */
public class MybatisUtils
{

    private static final List<ResultMapping> EMPTY_RESULTMAPPING = new ArrayList<ResultMapping>(0);

    public static MappedStatement getMappedStatement(MappedStatement ms, String msId)
    {
        try
        {
            return ms.getConfiguration().getMappedStatement(msId);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static void addMappedStatement(Configuration conf, MappedStatement ms)
    {
        try
        {
            conf.addMappedStatement(ms);
        }
        catch (Exception e)
        {

        }
    }

    public static MappedStatement createMappedStatement(MappedStatement ms, SqlSource sqlSource, String cacheSuffixKey,
            MappingEnum mappingType)
    {
        String id = ms.getId() + cacheSuffixKey;
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), id, sqlSource,
                ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0)
        {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties())
            {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        switch (mappingType)
        {
            case INT:
                List<ResultMap> resultMaps = new ArrayList<ResultMap>();
                ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), id, int.class, EMPTY_RESULTMAPPING)
                        .build();
                resultMaps.add(resultMap);
                builder.resultMaps(resultMaps);
                break;
            case DEFAULT:
                builder.resultMaps(ms.getResultMaps());
                break;
            default:
                break;
        }
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    public static List<ParameterMapping> plusTwoParameterToMapping(Configuration configuration, BoundSql boundSql)
    {
        List<ParameterMapping> newParameterMappings = new ArrayList<ParameterMapping>();
        newParameterMappings.addAll(boundSql.getParameterMappings());
        newParameterMappings.add(new ParameterMapping.Builder(configuration, Const.PARAMETER_FIRST, Integer.class)
                .build());
        newParameterMappings.add(new ParameterMapping.Builder(configuration, Const.PARAMETER_SECOND, Integer.class)
                .build());
        return newParameterMappings;
    }
}
