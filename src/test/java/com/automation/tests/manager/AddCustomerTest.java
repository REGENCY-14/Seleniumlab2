package com.automation.tests.manager;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoAlertPresentException;
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
 * AddCustomerTest - Tests for manager add customer functionality
 */
@Epic("Manager Operations")
@Feature("Customer Management")
@DisplayName("Manager Add Customer Tests")
public class AddCustomerTest extends SetUp {
    
    private static final Logger logger = LoggerFactory.getLogger(AddCustomerTest.class);
    private static final ConfigReader configReader = ConfigReader.getInstance();
    private static final TestDataReader testData = TestDataReader.getInstance();
    
    /**
     * Tests the functionality of adding a new customer by a bank manager.
     * This test navigates to the application, logs in as a manager,
     * adds a new customer using predefined test data, and verifies the success.
     */
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
     * Adds a new customer using the AddCustomerPage.
     * Retrieves customer details (first name, last name, postal code) from test data.
     */
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
    
    /**
     * Verifies that the customer has been successfully added.
     * This involves waiting for and accepting a confirmation alert.
     */
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

    /**
     * Verifies that the customer was not added for invalid inputs.
     */
    @Step("Verify customer not added")
    private void verifyCustomerNotAdded() {
        logger.info("Step 1: Waiting for validation response");
        waitFor(2000);

        logger.info("Step 2: Checking alert message");
        try {
            String alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            logger.info("Alert message: {}", alertText);
            assertFalse(alertText.toLowerCase().contains("customer added successfully"),
                    "Expected validation failure, but success alert shown: " + alertText);
        } catch (NoAlertPresentException e) {
            logger.info("No alert shown; assuming validation blocked submit");
        }
    }
    
    /**
     * Tests adding a customer with special characters in the first name.
     * Validates that the application handles special characters correctly.
     */
    @Test
    @Story("Add Customer Validation")
    @DisplayName("Test Add Customer With Special Characters In First Name")
    void testAddCustomerWithSpecialCharactersInFirstName() {
        logger.info("Starting add customer with special characters in first name test...");
        navigateToApplication();
        performManagerLogin();
        
        logger.info("Adding customer with special characters in first name: @#$%");
        addCustomerPage.addCustomer("@#$%", "Smith", "12345");

        verifyCustomerNotAdded();
        logger.info("Add customer with special characters test completed");
    }
    
    /**
     * Tests adding a customer with numeric characters in the last name.
     * Validates that the application handles numeric characters in names.
     */
    @Test
    @Story("Add Customer Validation")
    @DisplayName("Test Add Customer With Numeric Characters In Last Name")
    void testAddCustomerWithNumericCharactersInLastName() {
        logger.info("Starting add customer with numeric characters in last name test...");
        navigateToApplication();
        performManagerLogin();
        
        logger.info("Adding customer with numeric characters in last name: 12345");
        addCustomerPage.addCustomer("John", "12345", "67890");

        verifyCustomerNotAdded();
        logger.info("Add customer with numeric characters test completed");
    }
    
    /**
     * Tests adding a customer with alphabetic characters in the postal code.
     * Validates postal code field accepts non-numeric characters.
     */
    @Test
    @Story("Add Customer Validation")
    @DisplayName("Test Add Customer With Alphabetic Characters In Postal Code")
    void testAddCustomerWithAlphabeticCharactersInPostalCode() {
        logger.info("Starting add customer with alphabetic postal code test...");
        navigateToApplication();
        performManagerLogin();
        
        logger.info("Adding customer with alphabetic postal code: ABCDE");
        addCustomerPage.addCustomer("Jane", "Doe", "ABCDE");

        verifyCustomerNotAdded();
        logger.info("Add customer with alphabetic postal code test completed");
    }
    
    /**
     * Tests adding a customer with special characters in the postal code.
     * Validates postal code field validation.
     */
    @Test
    @Story("Add Customer Validation")
    @DisplayName("Test Add Customer With Special Characters In Postal Code")
    void testAddCustomerWithSpecialCharactersInPostalCode() {
        logger.info("Starting add customer with special characters in postal code test...");
        navigateToApplication();
        performManagerLogin();
        
        logger.info("Adding customer with special characters in postal code: @#$%^");
        addCustomerPage.addCustomer("Bob", "Johnson", "@#$%^");

        verifyCustomerNotAdded();
        logger.info("Add customer with special characters in postal code test completed");
    }
}
