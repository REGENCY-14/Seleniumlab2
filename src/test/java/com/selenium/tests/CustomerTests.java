package com.selenium.tests;

import com.selenium.base.BaseTest;
import com.selenium.pages.CustomerPage;
import com.selenium.pages.LoginPage;
import com.selenium.utils.ConfigReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CustomerTests class for testing customer banking functionality.
 * This class extends BaseTest and uses JUnit 5 for test automation.
 *
 * SOLID principles applied:
 * - Single Responsibility: Tests focus on customer-specific banking operations
 * - Open/Closed: Can be extended with additional customer tests
 * - Dependency Inversion: Depends on page objects and utilities
 *
 * @author Test Automation Team
 */
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
    @DisplayName("Test Deposit Functionality")
    void testDeposit() {
        // Initialize utilities and page objects
        configReader = ConfigReader.getInstance();
        String baseUrl = configReader.getBaseUrl();

        // Step 1: Navigate to base URL
        driver.get(baseUrl);

        // Step 2: Login as customer
        loginPage = new LoginPage(driver);
        loginPage.clickCustomerLogin();

        // Wait for page to load after login
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Initialize customer page object
        customerPage = new CustomerPage(driver);

        // Step 3: Login with specific customer
        String customerName = "Harry Potter";
        customerPage.loginCustomer(customerName);

        // Wait for login to complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Get initial balance
        String initialBalanceText = customerPage.getBalance();
        double initialBalance = Double.parseDouble(initialBalanceText);

        // Step 4: Perform deposit
        double depositAmount = 500.0;
        customerPage.deposit(depositAmount);

        // Wait for deposit to process
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Handle success alert if present
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // No alert present, continue
        }

        // Step 5: Verify balance was updated correctly
        String updatedBalanceText = customerPage.getBalance();
        double updatedBalance = Double.parseDouble(updatedBalanceText);
        double expectedBalance = initialBalance + depositAmount;

        assertEquals(expectedBalance, updatedBalance, 0.01, 
                "Balance should be updated with deposit amount");
        assertTrue(updatedBalance > initialBalance, 
                "Updated balance should be greater than initial balance");
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
    @DisplayName("Test Withdraw Functionality")
    void testWithdraw() {
        // Initialize utilities and page objects
        configReader = ConfigReader.getInstance();
        String baseUrl = configReader.getBaseUrl();

        // Step 1: Navigate to base URL
        driver.get(baseUrl);

        // Step 2: Login as customer
        loginPage = new LoginPage(driver);
        loginPage.clickCustomerLogin();

        // Wait for page to load after login
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Initialize customer page object
        customerPage = new CustomerPage(driver);

        // Step 3: Login with specific customer
        String customerName = "Harry Potter";
        customerPage.loginCustomer(customerName);

        // Wait for login to complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Get initial balance
        String initialBalanceText = customerPage.getBalance();
        double initialBalance = Double.parseDouble(initialBalanceText);

        // Step 4: Perform withdrawal
        double withdrawAmount = 100.0;
        customerPage.withdraw(withdrawAmount);

        // Wait for withdrawal to process
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Handle success alert if present
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // No alert present, continue
        }

        // Step 5: Verify balance was updated correctly
        String updatedBalanceText = customerPage.getBalance();
        double updatedBalance = Double.parseDouble(updatedBalanceText);
        double expectedBalance = initialBalance - withdrawAmount;

        assertEquals(expectedBalance, updatedBalance, 0.01, 
                "Balance should be reduced by withdrawal amount");
        assertTrue(updatedBalance < initialBalance, 
                "Updated balance should be less than initial balance");
        assertTrue(updatedBalance >= 0, 
                "Balance should never be negative");
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
    @DisplayName("Test View Transactions Functionality")
    void testViewTransactions() {
        // Initialize utilities and page objects
        configReader = ConfigReader.getInstance();
        String baseUrl = configReader.getBaseUrl();

        // Step 1: Navigate to base URL
        driver.get(baseUrl);

        // Step 2: Login as customer
        loginPage = new LoginPage(driver);
        loginPage.clickCustomerLogin();

        // Wait for page to load after login
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Initialize customer page object
        customerPage = new CustomerPage(driver);

        // Step 3: Login with specific customer
        String customerName = "Harry Potter";
        customerPage.loginCustomer(customerName);

        // Wait for login to complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Step 4: Perform a transaction first to ensure there's data
        customerPage.deposit(250.0);

        // Wait for deposit
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Handle alert
        try {
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // No alert present
        }

        // Step 5: View transactions
        customerPage.viewTransactions();

        // Wait for transactions to load
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Step 6: Verify transactions page is displayed
        // Check for transaction table or transaction elements
        int transactionRows = driver.findElements(
                org.openqa.selenium.By.xpath("//table//tbody//tr")).size();

        assertTrue(transactionRows > 0, 
                "Transaction history should display at least one transaction");
        
        // Verify current page contains transaction information
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Transactions") || pageSource.contains("transaction") || transactionRows > 0,
                "Transaction information should be visible on the page");
    }
}
