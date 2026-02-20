package com.selenium.tests;

import com.selenium.pages.BasePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Sample test class demonstrating SOLID principles
 */
public class SampleTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(SampleTest.class);

    @Test
    public void testPageTitle() {
        logger.info("Starting testPageTitle");
        BasePage basePage = new BasePage(driver, waitStrategy);
        basePage.navigateTo("https://www.google.com");
        String title = basePage.getPageTitle();
        Assert.assertNotNull(title, "Page title should not be null");
        logger.info("Test passed: Page title is {}", title);
    }

    @Test
    public void testCurrentUrl() {
        logger.info("Starting testCurrentUrl");
        BasePage basePage = new BasePage(driver, waitStrategy);
        basePage.navigateTo("https://www.github.com");
        String currentUrl = basePage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("github"), "URL should contain 'github'");
        logger.info("Test passed: Current URL is {}", currentUrl);
    }
}
