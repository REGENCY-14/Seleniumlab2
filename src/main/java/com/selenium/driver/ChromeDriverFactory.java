package com.selenium.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ChromeDriverFactory - Implementation for creating Chrome WebDriver
 * Follows: Single Responsibility & Interface Segregation
 */
public class ChromeDriverFactory implements DriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(ChromeDriverFactory.class);

    @Override
    public WebDriver createDriver() {
        logger.info("Creating Chrome WebDriver instance");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("Chrome WebDriver created successfully");
        return driver;
    }
}
