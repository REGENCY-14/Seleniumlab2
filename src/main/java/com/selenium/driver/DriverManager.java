package com.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DriverManager - Manages WebDriver lifecycle
 * Follows: Single Responsibility Principle
 */
public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private WebDriver driver;
    private final DriverFactory driverFactory;

    public DriverManager(DriverFactory driverFactory) {
        this.driverFactory = driverFactory;
    }

    public void initializeDriver() {
        if (driver == null) {
            driver = driverFactory.createDriver();
            logger.info("WebDriver initialized");
        }
    }

    public WebDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("WebDriver closed");
        }
    }
}
