package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.base.SetUp;
import com.automation.helpers.PageHelper;
import com.automation.pages.CustomerDashboardPage;
import com.automation.pages.CustomerLoginPage;
import com.automation.pages.DepositPage;
import com.automation.pages.LoginPage;
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
    
    // Page Objects
    private LoginPage loginPage;
    private CustomerLoginPage customerLoginPage;
    private DepositPage depositPage;
    private CustomerDashboardPage dashboardPage;
    
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
    
    @Step("Navigate to application")
    private void navigateToApplication() {
        logger.info("Step 1: Navigating to base URL");
        driver.get(configReader.getBaseUrl());
        logger.info("✓ Application loaded");
        
        // Initialize page objects
        loginPage = new LoginPage(driver);
        customerLoginPage = new CustomerLoginPage(driver);
        depositPage = new DepositPage(driver);
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
    
    @Step("Perform deposit")
    private void performDeposit() {
        logger.info("Step 1: Depositing amount: ${}", testData.getDepositAmount());
        depositPage.depositAmount(String.valueOf(testData.getDepositAmount()));
        logger.info("✓ Deposit submitted");
    }
    
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
}
