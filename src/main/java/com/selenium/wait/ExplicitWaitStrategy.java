package com.selenium.wait;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * ExplicitWaitStrategy - Implementation of explicit wait strategy
 * Follows: Single Responsibility Principle
 */
public class ExplicitWaitStrategy implements WaitStrategy {
    private static final int DEFAULT_WAIT_TIME = 10;

    @Override
    public void waitForElement(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    @Override
    public void waitForElementPresence(WebDriver driver, String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }
}
