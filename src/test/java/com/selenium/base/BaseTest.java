package com.selenium.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import com.selenium.utils.AllureReporting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInfo;
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
public class BaseTest {

    protected WebDriver driver;
    protected TestInfo testInfo;

    /**
     * Initialize WebDriver before each test.
     *
     * Sets up ChromeDriver using WebDriverManager for automatic driver management.
     * Maximizes the browser window for better test visibility.
     */
    @BeforeEach
    public void setUp(TestInfo testInfo) {
        this.testInfo = testInfo;
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
        if (testInfo != null && testInfo.getExecutionException().isPresent()) {
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
}
