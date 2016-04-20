package com.verdant.demo.common.thread.local;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016/4/15.
 */
public class ConnectionManager {

    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
        public Connection initialValue(String dbUrl) throws SQLException {
            return DriverManager.getConnection(dbUrl);
        }
    };

    public static Connection getConnection() {
        return connectionHolder.get();
    }
}