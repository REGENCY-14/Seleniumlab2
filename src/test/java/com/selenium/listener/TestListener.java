package com.selenium.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestListener - Listens to test execution events
 * Follows: Single Responsibility Principle
 */
public class TestListener implements ITestListener {
    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("TEST STARTED: {}", result.getName());
        logger.info("Description: {}", result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("✓ TEST PASSED: {} - Duration: {}ms", 
            result.getName(), 
            result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("✗ TEST FAILED: {}", result.getName());
        if (result.getThrowable() != null) {
            logger.error("Failure Reason:", result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("⊘ TEST SKIPPED: {}", result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("TEST FAILED WITHIN SUCCESS PERCENTAGE: {}", result.getName());
    }
}
