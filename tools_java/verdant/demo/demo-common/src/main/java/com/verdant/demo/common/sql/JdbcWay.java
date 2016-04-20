package com.verdant.demo.common.sql;

import java.sql.*;

/**
 * Author: verdant
 * Create: 2016/4/19
 * Func:   数据库操作
 */
public class JdbcWay {
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://10.10.124.10:3306/ems";
    private static final String DB_USERNAME = "ems";
    private static final String DB_PASSWORD = "123";
    private static final String DB_SQL = "select 1";

    /**
     * 事务操作
     * @param connection
     * @throws SQLException
     */
    private void transaction(Connection connection) throws SQLException{
        boolean autoCommit = connection.getAutoCommit();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.commit();
            connection.setAutoCommit(autoCommit);
        }
    }

    /**
     * 使用JDBC调用数据库
     * @param sql
     */
    private static void execute(String sql) {
        try {
            Class.forName(MYSQL_DRIVER);
            Connection connection =
                    DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement st = connection.createStatement();
            ResultSet existTable = st.executeQuery(sql);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        execute(DB_SQL);
    }
}
