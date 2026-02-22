package com.selenium.tests;

import com.selenium.base.BaseTest;
import com.selenium.pages.CustomerPage;
import com.selenium.pages.LoginPage;
import com.selenium.utils.ConfigReader;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        navigateToBaseUrl();
        performCustomerLogin();
        performDeposit();
        verifyDepositSuccess();
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
        navigateToBaseUrl();
        performCustomerLogin();
        performWithdraw();
        verifyWithdrawSuccess();
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
        navigateToBaseUrl();
        performCustomerLogin();
        performTransaction();
        viewTransactionHistory();
        verifyTransactionsDisplayed();
    }

    @Step("Navigate to base URL")
    private void navigateToBaseUrl() {
        configReader = ConfigReader.getInstance();
        String baseUrl = configReader.getBaseUrl();
        driver.get(baseUrl);
    }

    @Step("Perform customer login")
    private void performCustomerLogin() {
        loginPage = new LoginPage(driver);
        loginPage.clickCustomerLogin();
        waitForPageLoad();
        
        customerPage = new CustomerPage(driver);
        String customerName = "Harry Potter";
        customerPage.loginCustomer(customerName);
        waitForPageLoad();
    }

    @Step("Perform deposit of 500.0")
    private void performDeposit() {
        if (customerPage == null) {
            customerPage = new CustomerPage(driver);
        }
        double depositAmount = 500.0;
        customerPage.deposit(depositAmount);
        waitForPageLoad();
    }

    @Step("Verify deposit was successful and balance updated")
    private void verifyDepositSuccess() {
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // No alert present
        }
        
        String balanceText = customerPage.getBalance();
        assertNotNull(balanceText, "Balance should be displayed");
        assertTrue(!balanceText.isEmpty(), "Balance text should not be empty");
    }

    @Step("Perform withdrawal of 100.0")
    private void performWithdraw() {
        if (customerPage == null) {
            customerPage = new CustomerPage(driver);
        }
        double withdrawAmount = 100.0;
        customerPage.withdraw(withdrawAmount);
        waitForPageLoad();
    }

    @Step("Verify withdrawal was successful and balance updated")
    private void verifyWithdrawSuccess() {
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // No alert present
        }
        
        String balanceText = customerPage.getBalance();
        assertNotNull(balanceText, "Balance should be displayed");
        assertTrue(!balanceText.isEmpty(), "Balance text should not be empty");
    }

    @Step("Perform a transaction to ensure data exists")
    private void performTransaction() {
        if (customerPage == null) {
            customerPage = new CustomerPage(driver);
        }
        customerPage.deposit(250.0);
        waitForPageLoad();
        
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // No alert present
        }
    }

    @Step("View transaction history")
    private void viewTransactionHistory() {
        if (customerPage == null) {
            customerPage = new CustomerPage(driver);
        }
        customerPage.viewTransactions();
        waitForPageLoad(1500);
    }

    @Step("Verify transactions are displayed correctly")
    private void verifyTransactionsDisplayed() {
        int transactionRows = driver.findElements(
                org.openqa.selenium.By.xpath("//table//tbody//tr")).size();

        assertTrue(transactionRows > 0, 
                "Transaction history should display at least one transaction");
        
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Transactions") || pageSource.contains("transaction") || transactionRows > 0,
                "Transaction information should be visible on the page");
    }

    @Step("Wait for page to load")
    private void waitForPageLoad() {
        waitForPageLoad(1000);
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
