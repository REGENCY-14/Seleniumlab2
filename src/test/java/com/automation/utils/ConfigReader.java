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
        String url = properties.getProperty("baseUrl");
        if (url == null || url.isEmpty()) {
            // Fall back to reading from testdata.json if not in properties file
            url = TestDataReader.getInstance().getBaseUrl();
            logger.info("Base URL loaded from testdata.json");
        } else {
            logger.info("Base URL loaded from properties file");
        }
        return url;
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    // ============================================
    // Browser Configuration
    // ============================================
    public String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    // ============================================
    // Headless Mode Configuration
    // ============================================
    public boolean isHeadlessMode() {
        String headless = getProperty("headless");
        return "true".equalsIgnoreCase(headless);
    }
    
    // ============================================
    // Chrome Browser Options
    // ============================================
    public String[] getChromeOptions() {
        return new String[]{
            getProperty("chromeRemoteAllowOrigins"),
            getProperty("chromeDisableExtensions"),
            getProperty("chromeDisablePopupBlocking"),
            getProperty("chromeNoFirstRun"),
            getProperty("chromeNoDefaultBrowserCheck"),
            getProperty("chromeDisableDevShmUsage")
        };
    }
    
    public String[] getHeadlessChromeOptions() {
        return new String[]{
            getProperty("chromeHeadless"),
            getProperty("chromeNoSandbox"),
            getProperty("chromeDisableDevShmUsage"),
            getProperty("chromeDisableGpu"),
            getProperty("chromeWindowSize")
        };
    }
    
    // ============================================
    // Timeout Configuration
    // ============================================
    public int getExplicitWaitTimeout() {
        String timeout = getProperty("explicitWaitTimeout", "10");
        try {
            return Integer.parseInt(timeout);
        } catch (NumberFormatException e) {
            logger.warn("Invalid timeout value: {}, using default 10", timeout);
            return 10;
        }
    }
    
    public int getPageLoadTimeout() {
        String timeout = getProperty("pageLoadTimeout", "30");
        try {
            return Integer.parseInt(timeout);
        } catch (NumberFormatException e) {
            logger.warn("Invalid timeout value: {}, using default 30", timeout);
            return 30;
        }
    }
    
    public int getScriptTimeout() {
        String timeout = getProperty("scriptTimeout", "30");
        try {
            return Integer.parseInt(timeout);
        } catch (NumberFormatException e) {
            logger.warn("Invalid timeout value: {}, using default 30", timeout);
            return 30;
        }
    }
    
    // ============================================
    // Test Execution Configuration
    // ============================================
    public boolean shouldTakeScreenshotOnFailure() {
        String screenshot = getProperty("takeScreenshotOnFailure", "true");
        return "true".equalsIgnoreCase(screenshot);
    }
    

    // ============================================
    // Helper Methods
    // ============================================
    private String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        if (value == null || value.isEmpty()) {
            logger.debug("Property {} not found, using default: {}", key, defaultValue);
            return defaultValue;
        }
        return value;
    }
}
