package com.selenium.config;

/**
 * BrowserConfig - Configuration for different browsers
 * Follows: Single Responsibility Principle
 */
public enum BrowserType {
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge");

    private final String browserName;

    BrowserType(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserName() {
        return browserName;
    }
}
