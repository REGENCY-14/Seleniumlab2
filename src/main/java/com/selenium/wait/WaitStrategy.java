package com.selenium.wait;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * WaitStrategy - Interface for different wait strategies
 * Follows: Dependency Inversion Principle
 */
public interface WaitStrategy {
    void waitForElement(WebDriver driver, WebElement element);
    void waitForElementPresence(WebDriver driver, String locator);
}
