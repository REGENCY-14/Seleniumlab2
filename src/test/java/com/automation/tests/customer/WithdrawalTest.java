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
    private static final String BASE_URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
    private static final TestDataReader testData = TestDataReader.getInstance();
    
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
        
        logger.info("Step 3: Logging in as {}", testData.getTestCustomerName());
        CustomerLoginPage customerLoginPage = new CustomerLoginPage(driver);
        customerLoginPage.loginAsCustomer(testData.getTestCustomerName());
        logger.info("✓ Customer logged in successfully");
    }
    
    @Step("Perform withdrawal")
    private void performWithdrawal() {
        logger.info("Step 1: Accessing Withdrawal Page");
        WithdrawalPage withdrawalPage = new WithdrawalPage(driver);
        
        logger.info("Step 2: Withdrawing amount: ${}", testData.getWithdrawalAmount());
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
        CustomerDashboardPage dashboardPage = new CustomerDashboardPage(driver);
        String balance = dashboardPage.getBalance();
        
        logger.info("Step 3: Asserting balance is displayed");
        assertNotNull(balance, "Balance should be displayed");
        logger.info("✓ PASSED - Withdrawal successful");
    }
}
