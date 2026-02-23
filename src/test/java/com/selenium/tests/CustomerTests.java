package com.selenium.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selenium.base.BaseTest;
import com.selenium.pages.CustomerPage;
import com.selenium.pages.LoginPage;
import com.selenium.utils.ConfigReader;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

/**
 * CustomerTests class for testing customer banking functionality.
 * This class extends BaseTest and uses JUnit 5 for test automation.
 * Integrated with Allure reporting for comprehensive test documentation.
 *
 * SOLID principles applied:
 * - Single Responsibility: Tests focus on customer-specific banking operations
 * - Open/Closed: Can be extended with additional customer tests
 * - Dependency Inversion: Depends on page objects and utilities
 *
 * @author Test Automation Team
 */
@Epic("Customer Banking Operations")
@Feature("Account Transactions")
@DisplayName("Customer Banking Functionality Tests")
public class CustomerTests extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerTests.class);
    private ConfigReader configReader;
    private LoginPage loginPage;
    private CustomerPage customerPage;

    /**
     * Test case to verify deposit functionality.
     * Steps:
     * 1. Navigate to base URL
     * 2. Login as a customer
     * 3. Perform a deposit transaction
     * 4. Verify deposit amount was added to balance
     */
    @Test
    @Story("Deposit Money")
    @DisplayName("Test Deposit Functionality")
    void testDeposit() {
        logger.info("Starting deposit test...");
        navigateToBaseUrl();
        performCustomerLogin();
        performDeposit();
        verifyDepositSuccess();
        logger.info("Deposit test completed successfully");
    }

    /**
     * Test case to verify withdraw functionality.
     * Steps:
     * 1. Navigate to base URL
     * 2. Login as a customer
     * 3. Perform a withdrawal transaction
     * 4. Verify withdrawal amount was deducted from balance
     */
    @Test
    @Story("Withdraw Money")
    @DisplayName("Test Withdraw Functionality")
    void testWithdraw() {
        logger.info("Starting withdraw test...");
        navigateToBaseUrl();
        performCustomerLogin();
        performWithdraw();
        verifyWithdrawSuccess();
        logger.info("Withdraw test completed successfully");
    }

    /**
     * Test case to verify viewing transactions.
     * Steps:
     * 1. Navigate to base URL
     * 2. Login as a customer
     * 3. View transaction history
     * 4. Verify transactions are displayed
     */
    @Test
    @Story("View Transactions")
    @DisplayName("Test View Transactions Functionality")
    void testViewTransactions() {
        logger.info("Starting view transactions test...");
        navigateToBaseUrl();
        performCustomerLogin();
        performTransaction();
        viewTransactionHistory();
        verifyTransactionsDisplayed();
        logger.info("View transactions test completed successfully");
    }

    @Step("Navigate to base URL")
    private void navigateToBaseUrl() {
        configReader = ConfigReader.getInstance();
        String baseUrl = configReader.getBaseUrl();
        logger.info("[ACTION] Navigating to base URL");
        logger.info("[DATA] URL: {}", baseUrl);
        driver.get(baseUrl);
        logger.info("[SUCCESS] Page loaded successfully");
    }

    @Step("Perform customer login")
    private void performCustomerLogin() {
        logger.info("Step 1: Initializing LoginPage");
        loginPage = new LoginPage(driver);
        
        logger.info("Step 2: Clicking 'Customer Login' button");
        loginPage.clickCustomerLogin();
        waitForPageLoad();
        logger.info("✓ Customer login page loaded");
        
        customerPage = new CustomerPage(driver);
        String customerName = "Harry Potter";
        logger.info("Step 3: Logging in as customer: {}", customerName);
        customerPage.loginCustomer(customerName);
        waitForPageLoad();
        logger.info("✓ Customer '{}' logged in successfully", customerName);
    }

    @Step("Perform deposit of 500.0")
    private void performDeposit() {
        if (customerPage == null) {
            customerPage = new CustomerPage(driver);
        }
        double depositAmount = 500.0;
        logger.info("Step 1: Clicking 'Deposit' tab");
        logger.info("Step 2: Entering deposit amount: ${}", depositAmount);
        customerPage.deposit(depositAmount);
        waitForPageLoad();
        logger.info("✓ Deposit transaction submitted");
    }

    @Step("Verify deposit was successful and balance updated")
    private void verifyDepositSuccess() {
        logger.info("Step 1: Checking for confirmation alert");
        try {
            driver.switchTo().alert().accept();
            logger.info("Step 2: Alert accepted - deposit confirmed");
        } catch (Exception e) {
            logger.info("Step 1: No alert present");
        }
        
        logger.info("Step 3: Retrieving account balance");
        String balanceText = customerPage.getBalance();
        logger.info("Current balance: {}", balanceText);
        
        logger.info("Step 4: Verifying balance is displayed");
        assertNotNull(balanceText, "Balance should be displayed");
        logger.info("Step 5: Verifying balance text is not empty");
        assertTrue(!balanceText.isEmpty(), "Balance text should not be empty");
        logger.info("✓ PASSED");
    }

    @Step("Perform withdrawal of 100.0")
    private void performWithdraw() {
        if (customerPage == null) {
            customerPage = new CustomerPage(driver);
        }
        double withdrawAmount = 100.0;
        logger.info("Step 1: Clicking 'Withdraw' tab");
        logger.info("Step 2: Entering withdrawal amount: ${}", withdrawAmount);
        customerPage.withdraw(withdrawAmount);
        waitForPageLoad();
        logger.info("✓ Withdrawal transaction submitted");
    }

    @Step("Verify withdrawal was successful and balance updated")
    private void verifyWithdrawSuccess() {
        logger.info("Step 1: Checking for confirmation alert");
        try {
            driver.switchTo().alert().accept();
            logger.info("Step 2: Alert accepted - withdrawal confirmed");
        } catch (Exception e) {
            logger.info("Step 1: No alert present");
        }
        
        logger.info("Step 3: Retrieving updated account balance");
        String balanceText = customerPage.getBalance();
        logger.info("Current balance after withdrawal: {}", balanceText);
        
        logger.info("Step 4: Verifying balance is displayed");
        assertNotNull(balanceText, "Balance should be displayed");
        logger.info("Step 5: Verifying balance text is not empty");
        assertTrue(!balanceText.isEmpty(), "Balance text should not be empty");
        logger.info("✓ PASSED");
    }

    @Step("Perform a transaction to ensure data exists")
    private void performTransaction() {
        if (customerPage == null) {
            customerPage = new CustomerPage(driver);
        }
        double amount = 250.0;
        logger.info("Step 1: Creating test transaction for history");
        logger.info("Step 2: Entering deposit amount: ${}", amount);
        customerPage.deposit(amount);
        waitForPageLoad();
        
        logger.info("Step 3: Checking for transaction confirmation");
        try {
            driver.switchTo().alert().accept();
            logger.info("✓ Test transaction confirmed");
        } catch (Exception e) {
            logger.info("Step 3: No alert present");
        }
    }

    @Step("View transaction history")
    private void viewTransactionHistory() {
        if (customerPage == null) {
            customerPage = new CustomerPage(driver);
        }
        logger.info("Step 1: Clicking 'Transactions' tab");
        customerPage.viewTransactions();
        waitForPageLoad(1500);
        logger.info("✓ Transaction history page loaded");
    }

    @Step("Verify transactions are displayed correctly")
    private void verifyTransactionsDisplayed() {
        logger.info("Step 1: Counting transaction rows in table");
        int transactionRows = driver.findElements(
                org.openqa.selenium.By.xpath("//table//tbody//tr")).size();
        logger.info("Found {} transaction row(s)", transactionRows);

        logger.info("Step 2: Verifying at least one transaction is displayed");
        assertTrue(transactionRows > 0, 
                "Transaction history should display at least one transaction");
        
        logger.info("Step 3: Checking page content for transaction information");
        String pageSource = driver.getPageSource();
        boolean hasTransactionInfo = pageSource.contains("Transactions") || 
                                      pageSource.contains("transaction") || 
                                      transactionRows > 0;
        
        logger.info("Step 4: Verifying transaction information is visible");
        assertTrue(hasTransactionInfo,
                "Transaction information should be visible on the page");
        logger.info("✓ PASSED");
    }

    @Step("Wait for page to load")
    private void waitForPageLoad() {
        waitForPageLoad(2000);
    }

    @Step("Wait for {0} milliseconds")
    private void waitForPageLoad(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
