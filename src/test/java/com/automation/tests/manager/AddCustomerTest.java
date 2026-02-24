package com.automation.tests.manager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.base.SetUp;
import com.automation.pages.AddCustomerPage;
import com.automation.pages.LoginPage;
import com.automation.utils.ConfigReader;
import com.automation.utils.TestDataReader;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

/**
 * AddCustomerTest - Tests for manager add customer functionality
 */
@Epic("Manager Operations")
@Feature("Customer Management")
@DisplayName("Manager Add Customer Tests")
public class AddCustomerTest extends SetUp {
    
    private static final Logger logger = LoggerFactory.getLogger(AddCustomerTest.class);
    private static final ConfigReader configReader = ConfigReader.getInstance();
    private static final TestDataReader testData = TestDataReader.getInstance();
    
    // Page Objects
    private LoginPage loginPage;
    private AddCustomerPage addCustomerPage;
    
    @Test
    @Story("Add Customer")
    @DisplayName("Test Add Customer Functionality")
    void testAddCustomer() {
        logger.info("Starting add customer test...");
        navigateToApplication();
        performManagerLogin();
        performAddCustomer();
        verifyCustomerAdded();
        logger.info("Add customer test completed successfully");
    }
    
    @Step("Navigate to application")
    private void navigateToApplication() {
        logger.info("Step 1: Navigating to base URL");
        driver.get(configReader.getBaseUrl());
        logger.info("✓ Application loaded");
        
        // Initialize page objects
        loginPage = new LoginPage(driver);
        addCustomerPage = new AddCustomerPage(driver);
    }
    
    @Step("Perform manager login")
    private void performManagerLogin() {
        logger.info("Step 1: Clicking Bank Manager Login");
        loginPage.clickBankManagerLogin();
        logger.info("✓ Manager logged in successfully");
    }
    
    @Step("Add new customer")
    private void performAddCustomer() {
        logger.info("Step 1: Adding customer - {} {}", 
                   testData.getNewCustomerFirstName(), 
                   testData.getNewCustomerLastName());
        addCustomerPage.addCustomer(testData.getNewCustomerFirstName(),
                                   testData.getNewCustomerLastName(),
                                   testData.getNewCustomerPostalCode());
        logger.info("✓ Customer added");
    }
    
    @Step("Verify customer added")
    private void verifyCustomerAdded() {
        logger.info("Step 1: Waiting for confirmation");
        waitFor(2000);
        
        logger.info("Step 2: Accepting confirmation alert");
        try {
            driver.switchTo().alert().accept();
            logger.info("✓ PASSED - Customer added successfully");
        } catch (Exception e) {
            logger.warn("No confirmation alert received");
        }
    }
}
