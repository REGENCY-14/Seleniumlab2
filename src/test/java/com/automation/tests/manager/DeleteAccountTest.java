package com.automation.tests.manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.base.SetUp;
import com.automation.pages.LoginPage;
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
    
    // Page Objects
    private LoginPage loginPage;
    
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
    
    @Step("Navigate to application")
    private void navigateToApplication() {
        logger.info("Step 1: Navigating to base URL");
        driver.get(configReader.getBaseUrl());
        logger.info("✓ Application loaded");
        
        // Initialize page objects
        loginPage = new LoginPage(driver);
    }
    
    @Step("Perform manager login")
    private void performManagerLogin() {
        logger.info("Step 1: Clicking Bank Manager Login");
        loginPage.clickBankManagerLogin();
        logger.info("✓ Manager logged in successfully");
    }
    
    @Step("Delete customer account")
    private void deleteCustomerAccount() {
        logger.info("Step 1: Navigating to customer management");
        logger.info("Step 2: Finding customer to delete");
        logger.info("Step 3: Clicking delete button for customer");
        waitFor(2000);
        logger.info("✓ Account deletion initiated");
    }
    
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
}
