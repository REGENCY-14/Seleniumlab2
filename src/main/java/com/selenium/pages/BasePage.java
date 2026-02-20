package com.selenium.pages;

import com.selenium.wait.WaitStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Page class for all page objects
 * Follows: Single Responsibility & Dependency Inversion Principle
 */
public class BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WaitStrategy waitStrategy;

    public BasePage(WebDriver driver, WaitStrategy waitStrategy) {
        this.driver = driver;
        this.waitStrategy = waitStrategy;
        PageFactory.initElements(driver, this);
        logger.info("Page initialized: {}", this.getClass().getName());
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void navigateTo(String url) {
        logger.info("Navigating to: {}", url);
        driver.navigate().to(url);
    }
}
