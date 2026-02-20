package com.xyzbank.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DriverFactory - Factory class for creating and managing WebDriver instances
 * Follows Factory Pattern and Single Responsibility Principle
 * 
 * Features:
 * - WebDriverManager for automatic driver management
 * - Support for headless mode
 * - Proper driver initialization and cleanup
 */
public class DriverFactory {
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    /**
     * Creates and returns a ChromeDriver instance
     * Uses WebDriverManager for automatic driver management
     * 
     * @return WebDriver instance configured with standard options
     */
    public static WebDriver createDriver() {
        logger.info("Initializing ChromeDriver with standard configuration");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(getChromeOptions());
        driver.manage().window().maximize();
        logger.info("ChromeDriver initialized successfully");
        return driver;
    }

    /**
     * Creates and returns a ChromeDriver instance in headless mode
     * Useful for CI/CD pipelines and Docker environments
     * 
     * @return WebDriver instance configured for headless mode
     */
    public static WebDriver createHeadlessDriver() {
        logger.info("Initializing ChromeDriver in headless mode");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(getHeadlessChromeOptions());
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        logger.info("Headless ChromeDriver initialized successfully");
        return driver;
    }

    /**
     * Returns standard Chrome options for regular execution
     * 
     * @return ChromeOptions configured for standard testing
     */
    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        return options;
    }

    /**
     * Returns Chrome options configured for headless mode
     * Optimized for CI/CD environments without GUI
     * 
     * @return ChromeOptions configured for headless execution
     */
    private static ChromeOptions getHeadlessChromeOptions() {
        ChromeOptions options = getChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        return options;
    }

    /**
     * Safely closes the WebDriver and releases resources
     * 
     * @param driver WebDriver instance to close
     */
    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            try {
                driver.quit();
                logger.info("WebDriver closed successfully");
            } catch (Exception e) {
                logger.error("Error while closing WebDriver: {}", e.getMessage());
            }
        }
    }
}
