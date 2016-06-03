/*
 * 文  件  名：CustomerProviderSqlSource.java
 * 版         权：Copyright 2014 GSOFT Tech.Co.Ltd.All Rights Reserved.
 * 描         述：
 * 修  改  人：hadoop
 * 修改时间：2015年2月11日
 * 修改内容：新增
 */
package com.verdant.jools.db.orm.mybatis.sqlsource;

import com.verdant.jools.db.orm.mybatis.dialect.Dialect;
import com.verdant.jools.db.orm.mybatis.constant.Const;
import com.verdant.jools.db.orm.mybatis.utils.MybatisUtils;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import java.util.Map;


/**
 *
 * @author verdant
 * @since 2015年2月11日
 */
public class CustomerProviderSqlSource implements SqlSource
{

    private Configuration configuration;

    private ProviderSqlSource providerSqlSource;

    private Boolean count;

    private Dialect dialect;

    public CustomerProviderSqlSource(Configuration configuration, ProviderSqlSource providerSqlSource, Boolean count,
            Dialect dialect)
    {
        this.configuration = configuration;
        this.providerSqlSource = providerSqlSource;
        this.count = count;
        this.dialect = dialect;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject)
    {
        BoundSql boundSql = null;
        if (parameterObject instanceof Map && ((Map) parameterObject).containsKey(Const.PROVIDER_OBJECT))
        {
            boundSql = providerSqlSource.getBoundSql(((Map) parameterObject).get(Const.PROVIDER_OBJECT));
        }
        else
        {
            boundSql = providerSqlSource.getBoundSql(parameterObject);
        }
        if (count)
        {
            return new BoundSql(configuration,dialect.generateCountSQL(boundSql.getSql()) /*parser.getCountSql(boundSql.getSql())*/, boundSql.getParameterMappings(),
                    parameterObject);
        }
        else
        {
            return new BoundSql(configuration,dialect.generatePageSQL(boundSql.getSql())/* parser.getPageSql(boundSql.getSql())*/,
                    MybatisUtils.plusTwoParameterToMapping(configuration, boundSql)
//                    parser.getPageParameterMapping(configuration, boundSql)
                    , parameterObject);
        }
    }

}
