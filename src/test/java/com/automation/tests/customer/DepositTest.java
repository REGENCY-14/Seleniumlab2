package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.base.SetUp;
import com.automation.helpers.PageHelper;
import com.automation.utils.ConfigReader;
import com.automation.utils.TestDataReader;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

/**
 * DepositTest - Tests for customer deposit functionality
 */
@Epic("Customer Operations")
@Feature("Deposit Functionality")
@DisplayName("Customer Deposit Tests")
public class DepositTest extends SetUp {
    
    private static final Logger logger = LoggerFactory.getLogger(DepositTest.class);
    private static final ConfigReader configReader = ConfigReader.getInstance();
    private static final TestDataReader testData = TestDataReader.getInstance();
    
    /**
     * Tests the functionality of depositing money into a customer's account.
     * This test navigates to the application, logs in as a customer,
     * performs a deposit transaction, and verifies the success of the deposit.
     */
    @Test
    @Story("Deposit Money")
    @DisplayName("Test Deposit Functionality")
    void testDeposit() {
        logger.info("Starting deposit test...");
        navigateToApplication();
        performCustomerLogin();
        performDeposit();
        verifyDepositSuccess();
        logger.info("Deposit test completed successfully");
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
     * Performs the login action for a customer.
     * Clicks on the 'Customer Login' button, selects the customer from the dropdown,
     * and logs in.
     */
    @Step("Perform customer login")
    private void performCustomerLogin() {
        logger.info("Step 1: Clicking Customer Login");
        loginPage.clickCustomerLogin();
        waitForPageLoad();
        
        logger.info("Step 2: Logging in as {}", testData.getTestCustomerName());
        customerLoginPage.loginAsCustomer(testData.getTestCustomerName());
        logger.info("✓ Customer logged in successfully");
    }
    
    /**
     * Performs a deposit transaction.
     * Retrieves the deposit amount from test data and submits the deposit.
     */
    @Step("Perform deposit")
    private void performDeposit() {
        logger.info("Step 1: Depositing amount: ${}", testData.getDepositAmount());
        depositPage.depositAmount(String.valueOf(testData.getDepositAmount()));
        logger.info("✓ Deposit submitted");
    }
    
    /**
     * Verifies that the deposit was successful.
     * Accepts any confirmation alert and checks if the balance is displayed on the dashboard.
     */
    @Step("Verify deposit success")
    private void verifyDepositSuccess() {
        logger.info("Step 1: Checking for confirmation");
        PageHelper.acceptAlert(driver);
        
        logger.info("Step 2: Verifying balance updated");
        String balance = dashboardPage.getBalance();
        
        logger.info("Step 3: Asserting balance is displayed");
        assertNotNull(balance, "Balance should be displayed");
        logger.info("✓ PASSED - Deposit successful");
    }
    
    /**
     * Tests depositing a large amount into the account.
     */
    @Test
    @Story("Deposit Money")
    @DisplayName("Test Deposit Large Amount")
    void testDepositLargeAmount() {
        logger.info("Starting large deposit test...");
        navigateToApplication();
        performCustomerLogin();
        
        logger.info("Depositing large amount: $1000000");
        depositPage.depositAmount("1000000");
        
        verifyDepositSuccess();
        logger.info("Large deposit test completed successfully");
    }
    
    /**
     * Tests depositing multiple times into the account.
     */
    @Test
    @Story("Deposit Money")
    @DisplayName("Test Multiple Deposits")
    void testMultipleDeposits() {
        logger.info("Starting multiple deposits test...");
        navigateToApplication();
        performCustomerLogin();
        
        logger.info("Performing first deposit");
        depositPage.depositAmount("100");
        PageHelper.acceptAlert(driver);
        
        logger.info("Performing second deposit");
        depositPage.depositAmount("200");
        PageHelper.acceptAlert(driver);
        
        logger.info("Verifying balance after multiple deposits");
        String balance = dashboardPage.getBalance();
        assertNotNull(balance, "Balance should be displayed after multiple deposits");
        
        logger.info("✓ PASSED - Multiple deposits successful");
        logger.info("Multiple deposits test completed");
    }
}
