package com.selenium.assertion;

import org.testng.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CustomAssertions - Custom assertion methods
 * Follows: Single Responsibility Principle
 */
public class CustomAssertions {
    private static final Logger logger = LoggerFactory.getLogger(CustomAssertions.class);

    public static void assertStringContains(String text, String substring, String message) {
        logger.info("Verifying: '{}' contains '{}'", text, substring);
        Assert.assertTrue(text.contains(substring), message);
    }

    public static void assertUrlContains(String url, String expectedPart, String message) {
        logger.info("Verifying URL: '{}' contains '{}'", url, expectedPart);
        Assert.assertTrue(url.contains(expectedPart), message);
    }

    public static void assertNotEmpty(String value, String message) {
        logger.info("Verifying string is not empty");
        Assert.assertFalse(value == null || value.isEmpty(), message);
    }

    public static void assertTrue(boolean condition, String message) {
        logger.info("Verifying condition: {}", message);
        Assert.assertTrue(condition, message);
    }

    public static void assertEquals(String actual, String expected, String message) {
        logger.info("Verifying: expected='{}', actual='{}'", expected, actual);
        Assert.assertEquals(actual, expected, message);
    }
}
