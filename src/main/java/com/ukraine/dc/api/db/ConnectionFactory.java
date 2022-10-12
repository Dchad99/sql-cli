package com.ukraine.dc.api.db;

import com.ukraine.dc.api.exception.ResourceNotFoundException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory implements DataSource{
    private final DbConfig dbConfig;

    public ConnectionFactory() {
        dbConfig = new DbConfig();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the DB.", e);
        }
    }

    private static final class DbConfig {
        private String url;
        private String user;
        private String password;

        public DbConfig() {
            readProperties();
        }

        public String getUrl() {
            return url;
        }

        public String getUser() {
            return user;
        }

        public String getPassword() {
            return password;
        }

        private void readProperties() {
            try (var input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
                Properties props = new Properties();
                props.load(input);
                this.url = props.getProperty("url");
                this.user = props.getProperty("user");
                this.password = props.getProperty("password");
            } catch (IOException exception) {
                throw new ResourceNotFoundException("Failed to read properties file", exception);
            }
        }

    }

}
