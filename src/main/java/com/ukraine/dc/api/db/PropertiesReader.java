package com.ukraine.dc.api.db;

import com.ukraine.dc.api.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    public Properties readPropertiesFile() {
        try (var input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties props = new Properties();
            props.load(input);
            return props;
        } catch (IOException exception) {
            throw new ResourceNotFoundException("Failed to read properties file", exception);
        }
    }
}
