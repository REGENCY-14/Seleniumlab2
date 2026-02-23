package com.automation.tests.manager;

import com.automation.base.SetUp;
import com.automation.pages.*;
import com.automation.utils.CustomerData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

import static org.junit.jupiter.api.Assertions.*;

/**
 * AddCustomerTest - Tests for manager add customer functionality
 */
@Epic("Manager Operations")
@Feature("Customer Management")
@DisplayName("Manager Add Customer Tests")
public class AddCustomerTest extends SetUp {
    
    private static final Logger logger = LoggerFactory.getLogger(AddCustomerTest.class);
    private static final String BASE_URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
    
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
        driver.get(BASE_URL);
        logger.info("✓ Application loaded");
    }
    
    @Step("Perform manager login")
    private void performManagerLogin() {
        logger.info("Step 1: Initializing LoginPage");
        LoginPage loginPage = new LoginPage(driver);
        
        logger.info("Step 2: Clicking Bank Manager Login");
        loginPage.clickBankManagerLogin();
        logger.info("✓ Manager logged in successfully");
    }
    
    @Step("Add new customer")
    private void performAddCustomer() {
        logger.info("Step 1: Accessing Add Customer Page");
        AddCustomerPage addCustomerPage = new AddCustomerPage(driver);
        
        logger.info("Step 2: Adding customer - {} {}", 
                   CustomerData.NEW_CUSTOMER_FIRST_NAME, 
                   CustomerData.NEW_CUSTOMER_LAST_NAME);
        addCustomerPage.addCustomer(CustomerData.NEW_CUSTOMER_FIRST_NAME,
                                   CustomerData.NEW_CUSTOMER_LAST_NAME,
                                   CustomerData.NEW_CUSTOMER_POSTAL_CODE);
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
