package com.selenium.driver;

import org.openqa.selenium.WebDriver;

/**
 * DriverFactory - Factory interface for creating WebDriver instances
 * Follows: Single Responsibility & Open/Closed Principle
 */
public interface DriverFactory {
    WebDriver createDriver();
}
