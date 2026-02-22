package com.selenium.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Objects;

/**
 * ConfigReader utility class for reading configuration properties
 * from config.properties file located in resources folder.
 *
 * This class follows the SOLID principles:
 * - Single Responsibility: Only manages configuration reading
 * - Open/Closed: Can be extended for additional configurations
 * - Dependency Inversion: Depends on abstraction (Properties)
 *
 * @author Test Automation Team
 */
public class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();
    private static ConfigReader instance;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private ConfigReader() {
        loadProperties();
    }

    /**
     * Get singleton instance of ConfigReader.
     *
     * @return ConfigReader singleton instance
     */
    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    /**
     * Load properties from config.properties file in resources folder.
     *
     * @throws RuntimeException if config.properties file is not found
     */
    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException("config.properties file not found in resources folder");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading config.properties file: " + e.getMessage(), e);
        }
    }

    /**
     * Get the base URL from configuration.
     *
     * @return base URL value
     * @throws RuntimeException if baseUrl property is not defined
     */
    public String getBaseUrl() {
        String baseUrl = properties.getProperty("baseUrl");
        if (baseUrl == null || baseUrl.trim().isEmpty()) {
            throw new RuntimeException("baseUrl property not found in config.properties");
        }
        return baseUrl.trim();
    }

    /**
     * Get the browser type from configuration.
     *
     * @return browser type (e.g., chrome, firefox, edge)
     * @throws RuntimeException if browser property is not defined
     */
    public String getBrowser() {
        String browser = properties.getProperty("browser");
        if (browser == null || browser.trim().isEmpty()) {
            throw new RuntimeException("browser property not found in config.properties");
        }
        return browser.trim().toLowerCase();
    }

    /**
     * Get any custom property by key.
     *
     * @param key the property key
     * @return the property value, or null if not found
     */
    public String getProperty(String key) {
        Objects.requireNonNull(key, "Property key cannot be null");
        return properties.getProperty(key);
    }

    /**
     * Get any custom property by key with default value.
     *
     * @param key the property key
     * @param defaultValue the default value if key is not found
     * @return the property value or defaultValue if not found
     */
    public String getProperty(String key, String defaultValue) {
        Objects.requireNonNull(key, "Property key cannot be null");
        return properties.getProperty(key, defaultValue);
    }
}
