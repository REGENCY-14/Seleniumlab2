package com.selenium.base;

import java.time.Duration;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selenium.utils.AllureReporting;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * BaseTest class that provides WebDriver initialization and cleanup
 * for all Selenium test classes.
 *
 * This class follows the SOLID principles:
 * - Single Responsibility: Manages WebDriver lifecycle
 * - Open/Closed: Can be extended by test classes
 * - Liskov Substitution: Test classes can safely extend this class
 *
 * @author Test Automation Team
 */
@ExtendWith(BaseTest.TestFailureWatcher.class)
public class BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected WebDriver driver;
    protected TestInfo testInfo;
    private static boolean testFailed = false;

    /**
     * Initialize WebDriver before each test.
     *
     * Sets up ChromeDriver using WebDriverManager for automatic driver management.
     * Maximizes the browser window for better test visibility.
     */
    @BeforeEach
    public void setUp(TestInfo testInfo) {
        this.testInfo = testInfo;
        testFailed = false;
        
        String testName = testInfo.getDisplayName();
        logger.info("========================================");
        logger.info("STARTING TEST: {}", testName);
        logger.info("========================================");
        
        logger.debug("Setting up ChromeDriver...");
        WebDriverManager.chromedriver().clearDriverCache().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--no-first-run");
        options.addArguments("--no-default-browser-check");
        options.addArguments("--disable-dev-shm-usage");
        try {
            Path profileDir = Files.createTempDirectory("chrome-profile-");
            options.addArguments("--user-data-dir=" + profileDir.toAbsolutePath());
        } catch (Exception e) {
            logger.warn("Unable to create temp Chrome profile directory", e);
        }
        String headless = System.getProperty("headless", "false");
        if ("true".equalsIgnoreCase(headless)) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            logger.info("Running in HEADLESS mode for CI/CD");
        }
        driver = new ChromeDriver(options);
        if (!"true".equalsIgnoreCase(headless)) {
            driver.manage().window().maximize();
        }
        logger.info("Browser initialized and maximized");
        
        // Set implicit wait for element location
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Set page load timeout
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        logger.debug("Timeouts configured: implicit=10s, pageLoad=30s");
    }

    /**
     * Clear WebDriver resources after each test and capture screenshot on failure.
     *
     * Quits the driver to close all browser windows and free up system resources.
     * Captures and attaches screenshot to Allure report if test fails.
     */
    @AfterEach
    public void tearDown(TestInfo testInfo) {
        String testName = testInfo.getDisplayName();
        
        if (testFailed) {
            logger.error("TEST FAILED: {}", testName);
            try {
                logger.debug("Capturing debug information...");
                AllureReporting.attachCompleteDebugInfo(driver, testName);
                logger.info("Debug information captured and attached to report");
            } catch (Exception e) {
                logger.error("Error capturing debug information: {}", e.getMessage());
            }
        } else {
            logger.info("TEST PASSED: {}", testName);
        }

        if (driver != null) {
            logger.debug("Closing browser...");
            driver.quit();
            logger.info("Browser closed successfully");
        }
        
        logger.info("========================================");
        logger.info("FINISHED TEST: {}", testName);
        logger.info("========================================\n");
    }

    /**
     * TestWatcher to track test failures
     */
    static class TestFailureWatcher implements TestWatcher {
        private static final Logger watcherLogger = LoggerFactory.getLogger(TestFailureWatcher.class);
        
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            testFailed = true;
            String testName = context.getDisplayName();
            watcherLogger.error("Test '{}' failed with error: {}", testName, cause.getMessage());
        }
        
        @Override
        public void testSuccessful(ExtensionContext context) {
            String testName = context.getDisplayName();
            watcherLogger.info("✓ Test '{}' completed successfully", testName);
        }
    }}
