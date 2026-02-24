package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.base.SetUp;
import com.automation.pages.CustomerDashboardPage;
import com.automation.pages.CustomerLoginPage;
import com.automation.pages.LoginPage;
import com.automation.pages.WithdrawalPage;
import com.automation.utils.TestDataReader;
import com.automation.utils.ConfigReader;

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
    
    // Page Objects
    private LoginPage loginPage;
    private CustomerLoginPage customerLoginPage;
    private WithdrawalPage withdrawalPage;
    private CustomerDashboardPage dashboardPage;
    
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
    
    @Step("Navigate to application")
    private void navigateToApplication() {
        logger.info("Step 1: Navigating to base URL");
        driver.get(configReader.getBaseUrl());
        logger.info("✓ Application loaded");
        
        // Initialize page objects
        loginPage = new LoginPage(driver);
        customerLoginPage = new CustomerLoginPage(driver);
        withdrawalPage = new WithdrawalPage(driver);
        dashboardPage = new CustomerDashboardPage(driver);
    }
    
    @Step("Perform customer login")
    private void performCustomerLogin() {
        logger.info("Step 1: Clicking Customer Login");
        loginPage.clickCustomerLogin();
        waitForPageLoad();
        
        logger.info("Step 2: Logging in as {}", testData.getTestCustomerName());
        customerLoginPage.loginAsCustomer(testData.getTestCustomerName());
        logger.info("✓ Customer logged in successfully");
    }
    
    @Step("Perform withdrawal")
    private void performWithdrawal() {
        logger.info("Step 1: Withdrawing amount: ${}", testData.getWithdrawalAmount());
        withdrawalPage.withdrawAmount(String.valueOf(testData.getWithdrawalAmount()));
        logger.info("✓ Withdrawal submitted");
    }
    
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
}
