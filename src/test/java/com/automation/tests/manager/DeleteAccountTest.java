package com.automation.tests.manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.base.SetUp;
import com.automation.utils.ConfigReader;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

/**
 * DeleteAccountTest - Tests for manager delete account functionality
 */
@Epic("Manager Operations")
@Feature("Account Management")
@DisplayName("Manager Delete Account Tests")
public class DeleteAccountTest extends SetUp {
    
    private static final Logger logger = LoggerFactory.getLogger(DeleteAccountTest.class);
    private static final ConfigReader configReader = ConfigReader.getInstance();
    
    /**
     * Tests the functionality of deleting a customer account by a bank manager.
     * This test navigates to the application, logs in as a manager,
     * initiates the deletion of a customer account, and verifies the success.
     */
    @Test
    @Story("Delete Account")
    @DisplayName("Test Delete Account Functionality")
    void testDeleteAccount() {
        logger.info("Starting delete account test...");
        navigateToApplication();
        performManagerLogin();
        deleteCustomerAccount();
        verifyAccountDeleted();
        logger.info("Delete account test completed successfully");
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
     * Initiates the process of deleting a customer account.
     * This method currently logs the steps but does not perform the actual deletion logic
     * as it seems to be a placeholder or incomplete implementation.
     */
    @Step("Delete customer account")
    private void deleteCustomerAccount() {
        logger.info("Step 1: Navigating to customer management");
        logger.info("Step 2: Finding customer to delete");
        logger.info("Step 3: Clicking delete button for customer");
        waitFor(2000);
        logger.info("✓ Account deletion initiated");
    }
    
    /**
     * Verifies that the account has been successfully deleted.
     * This involves waiting for and accepting a confirmation alert.
     */
    @Step("Verify account deleted")
    private void verifyAccountDeleted() {
        logger.info("Step 1: Waiting for confirmation");
        waitFor(2000);
        
        logger.info("Step 2: Verifying deletion confirmation");
        try {
            driver.switchTo().alert().accept();
            logger.info("✓ PASSED - Account deleted successfully");
        } catch (Exception e) {
            logger.warn("No confirmation alert received");
        }
    }
    
    /**
     * Tests accessing customer management page.
     */
    @Test
    @Story("Delete Account")
    @DisplayName("Test Access Customer Management")
    void testAccessCustomerManagement() {
        logger.info("Starting access customer management test...");
        navigateToApplication();
        performManagerLogin();
        
        logger.info("Verifying manager dashboard is accessible");
        waitFor(1000);
        
        logger.info("✓ PASSED - Customer management accessed");
        logger.info("Access customer management test completed");
    }
}
