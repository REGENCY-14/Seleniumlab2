package com.selenium.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
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

    /**
     * Initialize WebDriver before each test.
     *
     * Sets up ChromeDriver using WebDriverManager for automatic driver management.
     * Maximizes the browser window for better test visibility.
     */
    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    /**
     * Clear WebDriver resources after each test.
     *
     * Quits the driver to close all browser windows and free up system resources.
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
