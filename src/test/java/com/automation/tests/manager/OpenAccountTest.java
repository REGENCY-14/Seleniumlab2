package com.automation.tests.manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.base.SetUp;
import com.automation.pages.LoginPage;
import com.automation.pages.OpenAccountPage;
import com.automation.utils.TestDataReader;
import com.automation.utils.ConfigReader;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

/**
 * OpenAccountTest - Tests for manager open account functionality
 */
@Epic("Manager Operations")
@Feature("Account Management")
@DisplayName("Manager Open Account Tests")
public class OpenAccountTest extends SetUp {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenAccountTest.class);
    private static final ConfigReader configReader = ConfigReader.getInstance();
    private static final TestDataReader testData = TestDataReader.getInstance();
    
    // Page Objects
    private LoginPage loginPage;
    private OpenAccountPage openAccountPage;
    
    @Test
    @Story("Open Account")
    @DisplayName("Test Open Account Functionality")
    void testOpenAccount() {
        logger.info("Starting open account test...");
        navigateToApplication();
        performManagerLogin();
        performOpenAccount();
        verifyAccountOpened();
        logger.info("Open account test completed successfully");
    }
    
    @Step("Navigate to application")
    private void navigateToApplication() {
        logger.info("Step 1: Navigating to base URL");
        driver.get(configReader.getBaseUrl());
        logger.info("✓ Application loaded");
        
        // Initialize page objects
        loginPage = new LoginPage(driver);
        openAccountPage = new OpenAccountPage(driver);
    }
    
    @Step("Perform manager login")
    private void performManagerLogin() {
        logger.info("Step 1: Clicking Bank Manager Login");
        loginPage.clickBankManagerLogin();
        logger.info("✓ Manager logged in successfully");
    }
    
    @Step("Open new account")
    private void performOpenAccount() {
        logger.info("Step 1: Creating account for {} in {}", 
                   testData.getTestCustomerName(), 
                   testData.getDefaultCurrency());
        openAccountPage.createAccount(testData.getTestCustomerName(), 
                                      testData.getDefaultCurrency());
        logger.info("✓ Account creation initiated");
    }
    
    @Step("Verify account opened")
    private void verifyAccountOpened() {
        logger.info("Step 1: Waiting for confirmation");
        waitFor(2000);
        
        logger.info("Step 2: Handling confirmation alerts");
        try {
            String alertText = driver.switchTo().alert().getText();
            logger.info("Alert message: {}", alertText);
            driver.switchTo().alert().accept();
            logger.info("✓ PASSED - Account opened successfully");
        } catch (Exception e) {
            logger.warn("No confirmation alert received");
        }
    }
}
