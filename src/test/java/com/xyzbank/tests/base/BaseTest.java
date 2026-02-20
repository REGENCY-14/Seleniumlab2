package com.xyzbank.tests.base;

import com.xyzbank.driver.DriverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BaseTest - Abstract base class for all test classes
 * Uses JUnit 5 annotations for setup and teardown
 * 
 * Features:
 * - Automatic WebDriver setup and teardown
 * - JUnit 5 @BeforeEach and @AfterEach lifecycle annotations
 * - Centralized driver management
 * - Logging for test execution
 */
public abstract class BaseTest {
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;
    protected boolean useHeadlessMode = false;

    /**
     * Setup method executed before each test
     * Initializes WebDriver based on configuration
     * 
     * Uses JUnit 5 @BeforeEach annotation for automatic invocation
     */
    @BeforeEach
    public void setUp() {
        logger.info("========================================");
        logger.info("Setting up test environment for: {}", getClass().getSimpleName());
        logger.info("========================================");
        
        try {
            // Initialize WebDriver - choose headless or standard mode
            if (useHeadlessMode) {
                driver = DriverFactory.createHeadlessDriver();
            } else {
                driver = DriverFactory.createDriver();
            }
            
            logger.info("WebDriver initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver: {}", e.getMessage(), e);
            throw new RuntimeException("WebDriver initialization failed", e);
        }
    }

    /**
     * Teardown method executed after each test
     * Closes WebDriver and releases resources
     * 
     * Uses JUnit 5 @AfterEach annotation for automatic invocation
     */
    @AfterEach
    public void tearDown() {
        logger.info("========================================");
        logger.info("Tearing down test environment");
        logger.info("========================================");
        
        try {
            if (driver != null) {
                DriverFactory.quitDriver(driver);
                logger.info("Test environment cleaned up successfully");
            }
        } catch (Exception e) {
            logger.error("Error during teardown: {}", e.getMessage(), e);
        }
    }

    /**
     * Navigate to a specific URL
     * 
     * @param url The URL to navigate to
     */
    protected void navigateTo(String url) {
        logger.info("Navigating to URL: {}", url);
        driver.navigate().to(url);
    }

    /**
     * Get the current page title
     * 
     * @return The page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }
}
