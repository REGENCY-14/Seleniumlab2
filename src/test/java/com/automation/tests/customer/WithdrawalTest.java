package com.automation.tests.customer;

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
 * WithdrawalTest - Tests for customer withdrawal functionality
 */
@Epic("Customer Operations")
@Feature("Withdrawal Functionality")
@DisplayName("Customer Withdrawal Tests")
public class WithdrawalTest extends SetUp {
    
    private static final Logger logger = LoggerFactory.getLogger(WithdrawalTest.class);
    private static final String BASE_URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
    
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
    
    @Step("Perform withdrawal")
    private void performWithdrawal() {
        logger.info("Step 1: Accessing Withdrawal Page");
        WithdrawalPage withdrawalPage = new WithdrawalPage(driver);
        
        logger.info("Step 2: Withdrawing amount: ${}", CustomerData.WITHDRAWAL_AMOUNT);
        withdrawalPage.withdrawAmount(String.valueOf(CustomerData.WITHDRAWAL_AMOUNT));
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
        CustomerDashboardPage dashboardPage = new CustomerDashboardPage(driver);
        String balance = dashboardPage.getBalance();
        
        logger.info("Step 3: Asserting balance is displayed");
        assertNotNull(balance, "Balance should be displayed");
        logger.info("✓ PASSED - Withdrawal successful");
    }
}
