package com.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Sample test class
 */
public class SampleTest extends BaseTest {

    @Test
    public void testPageTitle() {
        driver.navigate().to("https://www.google.com");
        String title = driver.getTitle();
        Assert.assertNotNull(title);
    }
}
