package com.selenium.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConfigReader - Reads configuration from properties files
 * Follows: Single Responsibility Principle
 */
public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    
    public static String getProperty(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            logger.warn("Property not found: {}", key);
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue) {
        String value = System.getProperty(key, defaultValue);
        logger.debug("Property: {} = {}", key, value);
        return value;
    }

    public static int getIntProperty(String key, int defaultValue) {
        try {
            String value = System.getProperty(key);
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            logger.error("Invalid integer value for property: {}", key);
            return defaultValue;
        }
    }
}
