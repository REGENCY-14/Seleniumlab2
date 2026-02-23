package com.automation.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

/**
 * PageHelper - Common page helper methods for test automation
 */
public class PageHelper {
    
    private static final Logger logger = LoggerFactory.getLogger(PageHelper.class);
    private static final int TIMEOUT = 10;
    
    /**
     * Wait for element to be visible
     */
    public static void waitForElementVisible(WebDriver driver, WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
            wait.until(ExpectedConditions.visibilityOf(element));
            logger.debug("Element is visible");
        } catch (Exception e) {
            logger.error("Timeout waiting for element visibility", e);
        }
    }
    
    /**
     * Wait for element to be clickable
     */
    public static void waitForElementClickable(WebDriver driver, WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            logger.debug("Element is clickable");
        } catch (Exception e) {
            logger.error("Timeout waiting for element clickability", e);
        }
    }
    
    /**
     * Handle alerts
     */
    public static String getAlertText(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            String alertText = driver.switchTo().alert().getText();
            logger.info("Alert text: {}", alertText);
            return alertText;
        } catch (Exception e) {
            logger.error("No alert present", e);
            return null;
        }
    }
    
    /**
     * Accept alert
     */
    public static void acceptAlert(WebDriver driver) {
        try {
            driver.switchTo().alert().accept();
            logger.info("Alert accepted");
        } catch (Exception e) {
            logger.debug("No alert to accept", e);
        }
    }
    
    /**
     * General wait
     */
    public static void waitForMilliseconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.warn("Thread sleep interrupted", e);
            Thread.currentThread().interrupt();
        }
    }
}
