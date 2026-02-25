package com.automation.base;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.pages.common.LoginPage;
import com.automation.pages.common.CustomerPage;
import com.automation.pages.customer.CustomerLoginPage;
import com.automation.pages.customer.DepositPage;
import com.automation.pages.customer.CustomerDashboardPage;
import com.automation.pages.customer.WithdrawalPage;
import com.automation.pages.customer.TransactionsPage;
import com.automation.pages.manager.AddCustomerPage;
import com.automation.pages.manager.OpenAccountPage;
import com.automation.utils.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;

/**
 * SetUp class for test configuration and WebDriver lifecycle management.
 * This base class provides common setup and teardown methods for all tests.
 * 
 * @author QA Team
 */
public class SetUp {
    
    protected WebDriver driver;
    
    // Page Objects
    protected LoginPage loginPage;
    protected CustomerLoginPage customerLoginPage;
    protected DepositPage depositPage;
    protected CustomerDashboardPage dashboardPage;
    protected WithdrawalPage withdrawalPage;
    protected TransactionsPage transactionsPage;
    protected AddCustomerPage addCustomerPage;
    protected OpenAccountPage openAccountPage;
    
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
        
        ConfigReader config = ConfigReader.getInstance();
        
        // Setup WebDriver
        WebDriverManager.chromedriver().clearDriverCache().setup();
        ChromeOptions options = new ChromeOptions();
        
        // Add common Chrome options
        for (String arg : config.getChromeOptions()) {
            if (arg != null && !arg.isEmpty()) {
                options.addArguments(arg);
            }
        }
        
        // Create temporary Chrome profile
        try {
            Path profileDir = Files.createTempDirectory("chrome-profile-");
            options.addArguments("--user-data-dir=" + profileDir.toAbsolutePath());
        } catch (Exception e) {
            logger.warn("Unable to create temp Chrome profile directory", e);
        }
        
        // Check if headless mode is requested (for CI/CD)
        if (config.isHeadlessMode()) {
            logger.info("Running in HEADLESS mode for CI/CD");
            for (String arg : config.getHeadlessChromeOptions()) {
                if (arg != null && !arg.isEmpty()) {
                    options.addArguments(arg);
                }
            }
        }
        
        driver = new ChromeDriver(options);
        
        // Maximize window if not in headless mode
        if (!config.isHeadlessMode()) {
            driver.manage().window().maximize();
        }
        
        logger.info("Browser initialized and configured");
        
        // Initialize page objects
        loginPage = new LoginPage(driver);
        customerLoginPage = new CustomerLoginPage(driver);
        depositPage = new DepositPage(driver);
        dashboardPage = new CustomerDashboardPage(driver);
        withdrawalPage = new WithdrawalPage(driver);
        transactionsPage = new TransactionsPage(driver);
        addCustomerPage = new AddCustomerPage(driver);
        openAccountPage = new OpenAccountPage(driver);
        logger.info("Page objects initialized");
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
     * Helper method to wait for page to load using WebDriverWait.
     * Waits for the document ready state to be 'complete'.
     */
    protected void waitForPageLoad() {
        try {
            ConfigReader config = ConfigReader.getInstance();
            int pageLoadTimeout = config.getPageLoadTimeout();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(pageLoadTimeout));
            
            // Wait for document ready state to be complete
            wait.until(driver -> {
                try {
                    return ((org.openqa.selenium.JavascriptExecutor) driver)
                            .executeScript("return document.readyState").equals("complete");
                } catch (Exception e) {
                    return false;
                }
            });
            logger.debug("Page load completed within {} seconds", pageLoadTimeout);
        } catch (Exception e) {
            logger.warn("Timeout waiting for page load", e);
        }
    }
    
    /**
     * Helper method to wait for specific duration with explicit wait.
     * Falls back to hard wait if milliseconds is less than 1000ms.
     * @param milliseconds Duration to wait in milliseconds
     */
    protected void waitFor(long milliseconds) {
        // For very short waits (< 1 second), use Thread.sleep
        if (milliseconds < 1000) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            // Convert to seconds for WebDriverWait
            long seconds = (milliseconds + 999) / 1000; // Round up
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
                // Wait for any condition - in this case, wait for document ready
                wait.until(driver -> {
                    try {
                        return ((org.openqa.selenium.JavascriptExecutor) driver)
                                .executeScript("return document.readyState").equals("complete");
                    } catch (Exception e) {
                        return true; // If we can't check, assume ready
                    }
                });
                logger.debug("Wait completed successfully");
            } catch (Exception e) {
                logger.warn("Timeout during wait", e);
            }
        }
    }
}
