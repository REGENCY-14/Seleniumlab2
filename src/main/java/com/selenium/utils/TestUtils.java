package com.selenium.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TestUtils - Utility methods for tests
 * Follows: Single Responsibility Principle
 */
public class TestUtils {
    private static final Logger logger = LoggerFactory.getLogger(TestUtils.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void delay(long milliseconds) {
        try {
            logger.debug("Waiting for {} milliseconds", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.error("Sleep interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    public static String getTimestamp() {
        return LocalDateTime.now().format(DATE_FORMATTER);
    }

    public static void printTestInfo(String testName) {
        logger.info("========================================");
        logger.info("Test: {}", testName);
        logger.info("Started at: {}", getTimestamp());
        logger.info("========================================");
    }

    public static void printTestResult(String testName, boolean passed) {
        logger.info("========================================");
        logger.info("Test: {} - {}", testName, passed ? "PASSED" : "FAILED");
        logger.info("Ended at: {}", getTimestamp());
        logger.info("========================================");
    }
}
