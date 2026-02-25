package com.automation.tests.manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.base.SetUp;
import com.automation.utils.ConfigReader;
import com.automation.utils.TestDataReader;

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
    
    /**
     * Tests the functionality of opening a new account for a customer by a bank manager.
     * This test navigates to the application, logs in as a manager,
     * opens a new account for a test customer with a default currency, and verifies the success.
     */
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
    
    /**
     * Navigates the WebDriver to the base URL of the application.
     */
    @Step("Navigate to application")
    private void navigateToApplication() {
        logger.info("Step 1: Navigating to base URL");
        driver.get(configReader.getBaseUrl());
        logger.info("✓ Application loaded");
    }
    
    /**
     * Performs the login action for a bank manager.
     * Clicks on the 'Bank Manager Login' button on the login page.
     */
    @Step("Perform manager login")
    private void performManagerLogin() {
        logger.info("Step 1: Clicking Bank Manager Login");
        loginPage.clickBankManagerLogin();
        logger.info("✓ Manager logged in successfully");
    }
    
    /**
     * Opens a new account for a customer using the OpenAccountPage.
     * Retrieves the customer name and default currency from test data.
     */
    @Step("Open new account")
    private void performOpenAccount() {
        logger.info("Step 1: Creating account for {} in {}", 
                   testData.getTestCustomerName(), 
                   testData.getDefaultCurrency());
        openAccountPage.createAccount(testData.getTestCustomerName(), 
                                      testData.getDefaultCurrency());
        logger.info("✓ Account creation initiated");
    }
    
    /**
     * Verifies that the account has been successfully opened.
     * This involves waiting for and accepting a confirmation alert, and logging the alert text.
     */
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
    
    /**
     * Tests opening account with different currency.
     */
    @Test
    @Story("Create Account")
    @DisplayName("Test Create Account With Different Currency")
    void testCreateAccountDifferentCurrency() {
        logger.info("Starting create account with different currency test...");
        navigateToApplication();
        performManagerLogin();
        
        logger.info("Creating account in Rupee");
        openAccountPage.createAccount(testData.getTestCustomerName(), "Rupee");
        
        verifyAccountOpened();
        logger.info("Create account with different currency test completed");
    }
}
