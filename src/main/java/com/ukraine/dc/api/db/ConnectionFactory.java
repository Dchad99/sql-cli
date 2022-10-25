package com.ukraine.dc.api.db;

import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@RequiredArgsConstructor
public class ConnectionFactory implements DataSource {
    private final Properties properties;

    public Connection getConnection() {
        try {
            var url = properties.getProperty("db.url");
            var user = properties.getProperty("db.user");
            var password = properties.getProperty("db.password");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the DB.", e);
        }
    }


}
