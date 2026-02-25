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
     * Waits for the specified element to be visible on the page.
     * Uses a default timeout of 10 seconds.
     *
     * @param driver  The WebDriver instance.
     * @param element The WebElement to wait for.
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
     * Waits for the specified element to be clickable.
     * Uses a default timeout of 10 seconds.
     *
     * @param driver  The WebDriver instance.
     * @param element The WebElement to wait for.
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
     * Retrieves the text from an alert dialog if one is present.
     * Waits up to 5 seconds for the alert to appear.
     *
     * @param driver The WebDriver instance.
     * @return The text of the alert, or null if no alert is present.
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
     * Accepts the currently displayed alert dialog.
     * If no alert is present, the exception is logged.
     *
     * @param driver The WebDriver instance.
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
     * Pauses the execution for a specified number of milliseconds.
     * This is a wrapper around Thread.sleep().
     *
     * @param milliseconds The number of milliseconds to wait.
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
