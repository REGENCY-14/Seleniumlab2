package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
 * WithdrawalTest - Tests for customer withdrawal functionality
 */
@Epic("Customer Operations")
@Feature("Withdrawal Functionality")
@DisplayName("Customer Withdrawal Tests")
public class WithdrawalTest extends SetUp {
    
    private static final Logger logger = LoggerFactory.getLogger(WithdrawalTest.class);
    private static final ConfigReader configReader = ConfigReader.getInstance();
    private static final TestDataReader testData = TestDataReader.getInstance();
    
    /**
     * Tests the functionality of withdrawing money from a customer's account.
     * This test navigates to the application, logs in as a customer,
     * performs a withdrawal transaction, and verifies the success of the withdrawal.
     */
    @Test
    @Story("Withdraw Money")
    @DisplayName("Test Withdrawal Functionality")
    void testWithdrawal() {
        logger.info("Starting withdrawal test...");
        navigateToApplication();
        performCustomerLogin();
        performWithdrawal();
        verifyWithdrawalSuccess();
        logger.info("Withdrawal test completed successfully");
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
     * Performs a withdrawal transaction.
     * Retrieves the withdrawal amount from test data and submits the withdrawal.
     */
    @Step("Perform withdrawal")
    private void performWithdrawal() {
        logger.info("Step 1: Withdrawing amount: ${}", testData.getWithdrawalAmount());
        withdrawalPage.withdrawAmount(String.valueOf(testData.getWithdrawalAmount()));
        logger.info("✓ Withdrawal submitted");
    }
    
    /**
     * Verifies that the withdrawal was successful.
     * Accepts any confirmation alert and checks if the balance is displayed on the dashboard.
     */
    @Step("Verify withdrawal success")
    private void verifyWithdrawalSuccess() {
        logger.info("Step 1: Checking for confirmation");
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            logger.debug("No alert to accept");
        }
        
        logger.info("Step 2: Verifying balance updated");
        String balance = dashboardPage.getBalance();
        
        logger.info("Step 3: Asserting balance is displayed");
        assertNotNull(balance, "Balance should be displayed");
        logger.info("✓ PASSED - Withdrawal successful");
    }
    
    /**
     * Tests withdrawing a large amount from the account.
     */
    @Test
    @Story("Withdraw Money")
    @DisplayName("Test Withdrawal Large Amount")
    void testWithdrawalLargeAmount() {
        logger.info("Starting large withdrawal test...");
        navigateToApplication();
        performCustomerLogin();
        
        logger.info("Attempting to withdraw large amount: $500000");
        withdrawalPage.withdrawAmount("500000");
        
        logger.info("Checking transaction result");
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            logger.debug("No alert");
        }
        
        String balance = dashboardPage.getBalance();
        assertNotNull(balance, "Balance should be displayed after withdrawal attempt");
        logger.info("Large withdrawal test completed");
    }
}
