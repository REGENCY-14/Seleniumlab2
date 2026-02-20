package com.selenium.tests;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Sample test class
 */
public class SampleTest extends BaseTest {

    @Test
    public void testPageTitle() {
        driver.navigate().to("https://www.google.com");
        String title = driver.getTitle();
        assertNotNull(title);
    }
}
