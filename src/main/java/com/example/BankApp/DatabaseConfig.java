package com.example.BankApp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {

    private final Properties properties;

    public DatabaseConfig() {
        properties = new Properties();
        try (InputStream input = new FileInputStream("application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return properties.getProperty("spring.datasource.url");
    }

    public String getUsername() {
        return properties.getProperty("spring.datasource.username");
    }

    public String getPassword() {
        return properties.getProperty("spring.datasource.password");
    }
}
