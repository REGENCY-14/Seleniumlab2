package com.automation.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;

/**
 * SetUp class for test configuration and WebDriver lifecycle management.
 * This base class provides common setup and teardown methods for all tests.
 * 
 * @author QA Team
 */
public class SetUp {
    
    protected WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(SetUp.class);
    
    /**
     * Setup method executed before each test.
     * Initializes WebDriver and navigates to base URL.
     */
    @BeforeEach
    @Step("Setup test environment")
    public void setUp(TestInfo testInfo) {
        logger.info("==============================================================================");
        logger.info("STARTING TEST: {}", testInfo.getDisplayName());
        logger.info("==============================================================================");
        
        // Setup WebDriver
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        
        // Maximize window and set timeouts
        driver.manage().window().maximize();
        logger.info("Browser initialized and maximized");
    }
    
    /**
     * Teardown method executed after each test.
     * Closes the WebDriver instance.
     */
    @AfterEach
    @Step("Cleanup test environment")
    public void tearDown(TestInfo testInfo) {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed successfully");
        }
        logger.info("==============================================================================");
        logger.info("FINISHED TEST: {}", testInfo.getDisplayName());
        logger.info("==============================================================================");
    }
    
    /**
     * Helper method to wait for page to load
     */
    protected void waitForPageLoad() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Helper method to wait for specific duration
     */
    protected void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
