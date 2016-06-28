package com.verdant.jools.db.orm.mybatis.dialect;

import org.apache.ibatis.session.Configuration;


public class DialectFactory {
    private static String dialectClass = null;
    private static Dialect dialect = null;

    public static Dialect buildDialect(Configuration configuration) {
        if (dialectClass == null) {
            synchronized (DialectFactory.class) {
                if (dialectClass == null) {
                    dialectClass = configuration.getVariables().getProperty("dialectClass");
                }
            }
        }
        if (dialect != null)
            return dialect;
        try {
            dialect = (Dialect) Class.forName(dialectClass).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("请检查 config.mybatis.xml 中  dialectClass 是否配置正确?");
        }
        return dialect;
    }
}
