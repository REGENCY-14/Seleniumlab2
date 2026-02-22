package com.selenium.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import com.selenium.utils.AllureReporting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    /**
     * Clear WebDriver resources after each test and capture screenshot on failure.
     *
     * Quits the driver to close all browser windows and free up system resources.
     * Captures and attaches screenshot to Allure report if test fails.
     */
    @AfterEach
    public void tearDown(TestInfo testInfo) {
        if (testFailed) {
            try {
                String testName = testInfo.getDisplayName();
                AllureReporting.attachCompleteDebugInfo(driver, testName);
            } catch (Exception e) {
                System.err.println("Error capturing debug information: " + e.getMessage());
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * TestWatcher to track test failures
     */
    static class TestFailureWatcher implements TestWatcher {
        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            testFailed = true;
        }
    }}
