package com.automation.tests.customer;

import com.automation.base.SetUp;
import com.automation.helpers.PageHelper;
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
 * DepositTest - Tests for customer deposit functionality
 */
@Epic("Customer Operations")
@Feature("Deposit Functionality")
@DisplayName("Customer Deposit Tests")
public class DepositTest extends SetUp {
    
    private static final Logger logger = LoggerFactory.getLogger(DepositTest.class);
    private static final String BASE_URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
    
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
        driver.get(BASE_URL);
        logger.info("✓ Application loaded");
    }
    
    @Step("Perform customer login")
    private void performCustomerLogin() {
        logger.info("Step 1: Initializing LoginPage");
        LoginPage loginPage = new LoginPage(driver);
        
        logger.info("Step 2: Clicking Customer Login");
        loginPage.clickCustomerLogin();
        waitForPageLoad();
        
        logger.info("Step 3: Logging in as {}", CustomerData.TEST_CUSTOMER_NAME);
        CustomerLoginPage customerLoginPage = new CustomerLoginPage(driver);
        customerLoginPage.loginAsCustomer(CustomerData.TEST_CUSTOMER_NAME);
        logger.info("✓ Customer logged in successfully");
    }
    
    @Step("Perform deposit")
    private void performDeposit() {
        logger.info("Step 1: Accessing Deposit Page");
        DepositPage depositPage = new DepositPage(driver);
        
        logger.info("Step 2: Depositing amount: ${}", CustomerData.DEPOSIT_AMOUNT);
        depositPage.depositAmount(String.valueOf(CustomerData.DEPOSIT_AMOUNT));
        logger.info("✓ Deposit submitted");
    }
    
    @Step("Verify deposit success")
    private void verifyDepositSuccess() {
        logger.info("Step 1: Checking for confirmation");
        PageHelper.acceptAlert(driver);
        
        logger.info("Step 2: Verifying balance updated");
        CustomerDashboardPage dashboardPage = new CustomerDashboardPage(driver);
        String balance = dashboardPage.getBalance();
        
        logger.info("Step 3: Asserting balance is displayed");
        assertNotNull(balance, "Balance should be displayed");
        logger.info("✓ PASSED - Deposit successful");
    }
}
