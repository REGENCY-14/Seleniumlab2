package com.automation.utils;

import java.io.FileInputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ConfigReader - Utility to read configuration from properties file
 */
public class ConfigReader {
    
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static ConfigReader instance;
    private Properties properties;
    
    private ConfigReader() {
        try {
            String configPath = "src/main/resources/config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            properties = new Properties();
            properties.load(fis);
            fis.close();
            logger.info("Properties loaded successfully");
        } catch (Exception e) {
            logger.error("Failed to load properties", e);
            properties = new Properties();
        }
    }
    
    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }
    
    public String getBaseUrl() {
        return properties.getProperty("base.url", "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
