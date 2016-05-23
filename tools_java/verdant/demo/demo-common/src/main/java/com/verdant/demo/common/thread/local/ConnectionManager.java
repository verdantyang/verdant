package com.verdant.demo.common.thread.local;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Author: verdant
 * Desc:   ThreadLocal应用
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
