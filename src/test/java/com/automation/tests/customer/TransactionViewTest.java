package com.automation.tests.customer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.base.SetUp;
import com.automation.pages.CustomerLoginPage;
import com.automation.pages.DepositPage;
import com.automation.pages.LoginPage;
import com.automation.pages.TransactionsPage;
import com.automation.utils.TestDataReader;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

/**
 * TransactionViewTest - Tests for viewing transaction history
 */
@Epic("Customer Operations")
@Feature("Transaction Viewing")
@DisplayName("Customer Transaction View Tests")
public class TransactionViewTest extends SetUp {
    
    private static final Logger logger = LoggerFactory.getLogger(TransactionViewTest.class);
    private static final String BASE_URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";
    private static final TestDataReader testData = TestDataReader.getInstance();
    
    // Page Objects
    private LoginPage loginPage;
    private CustomerLoginPage customerLoginPage;
    private DepositPage depositPage;
    private TransactionsPage transactionsPage;
    
    @Test
    @Story("View Transactions")
    @DisplayName("Test Transaction View Functionality")
    void testViewTransactions() {
        logger.info("Starting transaction view test...");
        navigateToApplication();
        performCustomerLogin();
        createTransaction();
        viewTransactionHistory();
        verifyTransactionsDisplayed();
        logger.info("Transaction view test completed successfully");
    }
    
    @Step("Navigate to application")
    private void navigateToApplication() {
        logger.info("Step 1: Navigating to base URL");
        driver.get(BASE_URL);
        logger.info("✓ Application loaded");
        
        // Initialize page objects
        loginPage = new LoginPage(driver);
        customerLoginPage = new CustomerLoginPage(driver);
        depositPage = new DepositPage(driver);
        transactionsPage = new TransactionsPage(driver);
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
    
    @Step("Create test transaction")
    private void createTransaction() {
        logger.info("Step 1: Creating deposit transaction for history");
        depositPage.depositAmount(String.valueOf(testData.getTestTransactionAmount()));
        
        logger.info("Step 2: Accepting confirm alert");
        try {
            driver.switchTo().alert().accept();
            // Wait for transaction to be recorded in the system
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.debug("No alert present");
        }
    }
    
    @Step("View transaction history")
    private void viewTransactionHistory() {
        logger.info("Step 1: Opening transaction history");
        transactionsPage.clickTransactionsTab();
        // Wait for transactions to load
        waitForPageLoad();
        logger.info("✓ Transaction history loaded");
    }
    
    @Step("Verify transactions displayed")
    private void verifyTransactionsDisplayed() {
        logger.info("Step 1: Checking transaction count");
        int transactionCount = transactionsPage.getTransactionCount();
        
        logger.info("Step 2: Verifying transactions exist - Count: {}", transactionCount);
        assertTrue(transactionCount > 0, "Transaction history should display at least one transaction");
        logger.info("✓ PASSED - Transactions displayed successfully");
    }
}
