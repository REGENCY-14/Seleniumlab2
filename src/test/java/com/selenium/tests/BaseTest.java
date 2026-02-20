package com.selenium.tests;

import com.selenium.driver.ChromeDriverFactory;
import com.selenium.driver.DriverManager;
import com.selenium.wait.ExplicitWaitStrategy;
import com.selenium.wait.WaitStrategy;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Base Test class for all test cases
 * Follows: Dependency Inversion Principle
 */
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected WebDriver driver;
    protected WaitStrategy waitStrategy;
    private DriverManager driverManager;

    @BeforeClass
    public void setUp() {
        logger.info("Setting up test environment");
        // Dependency Injection - loose coupling
        driverManager = new DriverManager(new ChromeDriverFactory());
        driverManager.initializeDriver();
        driver = driverManager.getDriver();
        waitStrategy = new ExplicitWaitStrategy();
        logger.info("Test setup completed");
    }

    @AfterClass
    public void tearDown() {
        logger.info("Tearing down test environment");
        if (driverManager != null) {
            driverManager.quitDriver();
        }
        logger.info("Test teardown completed");
    }
}
