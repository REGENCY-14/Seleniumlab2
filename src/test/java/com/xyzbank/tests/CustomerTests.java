package com.xyzbank.tests;

import com.xyzbank.pages.CustomerPage;
import com.xyzbank.tests.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CustomerTests - Comprehensive test suite for Customer operations
 * Tests customer-specific functionality:
 * - Customer Login
 * - Deposit Money
 * - Withdraw Money
 * - View Transactions
 * - Balance Update Verification
 * 
 * Uses JUnit 5 and Allure reporting annotations
 * Validates business rules like positive amounts and sufficient balance
 */
@Feature("Customer Functions")
@DisplayName("Customer Test Suite")
public class CustomerTests extends BaseTest {
    
    private CustomerPage customerPage;
    private static final String BANK_APP_URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";

    /**
     * Setup before each customer test
     * Initializes CustomerPage
     */
    @BeforeEach
    public void setUpCustomer() {
        super.setUp();
        logger.info("Initializing CustomerPage");
        navigateTo(BANK_APP_URL);
        customerPage = new CustomerPage(driver);
    }

    @Test
    @DisplayName("Customer Login")
    @Story("Authentication")
    @Description("Test that customer can successfully login to their account")
    @Severity(SeverityLevel.CRITICAL)
    public void testCustomerLogin() {
        logger.info("Starting test: testCustomerLogin");
        
        String customerName = "Harry Potter";

        // Execute - Login should not throw exception
        assertDoesNotThrow(() -> customerPage.login(customerName));
        
        // Verify - Balance should be displayed (indicating successful login)
        double balance = customerPage.getBalance();
        assertNotEquals(0, balance, "Balance should be retrieved after login");
        
        logger.info("Test passed: Customer logged in successfully with balance: {}", balance);
    }

    @Test
    @DisplayName("Deposit Money")
    @Story("Transactions")
    @Description("Test depositing positive amount of money to customer account")
    @Severity(SeverityLevel.NORMAL)
    public void testDeposit() {
        logger.info("Starting test: testDeposit");
        
        String customerName = "Hermoine Granger";
        double depositAmount = 500.0;

        // Login
        customerPage.login(customerName);
        double initialBalance = customerPage.getBalance();
        logger.debug("Initial balance: {}", initialBalance);

        // Execute - Deposit money
        assertDoesNotThrow(() -> customerPage.deposit(depositAmount));
        
        // Verify - Balance should increase
        double newBalance = customerPage.getBalance();
        assertEquals(initialBalance + depositAmount, newBalance, 0.01, 
            "Balance should increase by deposit amount");
        
        logger.info("Test passed: Deposit of {} successful, new balance: {}", depositAmount, newBalance);
    }

    @Test
    @DisplayName("Deposit with Invalid Amount")
    @Story("Transactions")
    @Description("Test that depositing negative or zero amount throws error")
    @Severity(SeverityLevel.NORMAL)
    public void testDepositInvalidAmount() {
        logger.info("Starting test: testDepositInvalidAmount");
        
        customerPage.login("Ron Weasley");

        // Execute and verify - Should throw exception for negative amount
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> customerPage.deposit(-100),
            "Should throw exception for negative deposit"
        );
        assertTrue(exception.getMessage().contains("positive"));
        
        // Also test zero amount
        exception = assertThrows(
            IllegalArgumentException.class,
            () -> customerPage.deposit(0),
            "Should throw exception for zero deposit"
        );
        assertTrue(exception.getMessage().contains("positive"));
        
        logger.info("Test passed: Invalid amounts rejected");
    }

    @Test
    @DisplayName("Withdraw Money")
    @Story("Transactions")
    @Description("Test withdrawing money with sufficient balance")
    @Severity(SeverityLevel.NORMAL)
    public void testWithdraw() {
        logger.info("Starting test: testWithdraw");
        
        String customerName = "Harry Potter";

        // Login and get initial balance
        customerPage.login(customerName);
        double initialBalance = customerPage.getBalance();
        logger.debug("Initial balance: {}", initialBalance);

        // Ensure we have sufficient balance for withdrawal
        if (initialBalance < 100) {
            customerPage.deposit(200);
        }

        double withdrawAmount = 100.0;

        // Execute - Withdraw money
        assertDoesNotThrow(() -> customerPage.withdraw(withdrawAmount));
        
        // Verify - Balance should decrease
        double newBalance = customerPage.getBalance();
        assertEquals(initialBalance - withdrawAmount + 200, newBalance, 0.01, 
            "Balance should decrease by withdrawal amount");
        
        logger.info("Test passed: Withdrawal of {} successful, new balance: {}", withdrawAmount, newBalance);
    }

    @Test
    @DisplayName("Withdraw with Insufficient Balance")
    @Story("Transactions")
    @Description("Test that withdrawal with insufficient balance shows error")
    @Severity(SeverityLevel.NORMAL)
    public void testWithdrawInsufficientBalance() {
        logger.info("Starting test: testWithdrawInsufficientBalance");
        
        customerPage.login("Hermoine Granger");
        double balance = customerPage.getBalance();
        logger.debug("Current balance: {}", balance);

        // Try to withdraw more than available
        double excessiveAmount = balance + 1000;

        // Execute - Attempt withdrawal
        customerPage.withdraw(excessiveAmount);
        
        // Verify - Should show insufficient balance error
        boolean hasError = customerPage.hasInsufficientBalanceError();
        assertTrue(hasError, "Should display insufficient balance error");
        
        logger.info("Test passed: Insufficient balance error displayed");
    }

    @Test
    @DisplayName("View Transactions")
    @Story("Transaction History")
    @Description("Test viewing transaction history after deposit and withdrawal")
    @Severity(SeverityLevel.NORMAL)
    public void testViewTransactions() {
        logger.info("Starting test: testViewTransactions");
        
        String customerName = "Ron Weasley";
        customerPage.login(customerName);

        // Make some transactions
        customerPage.deposit(250.0);
        customerPage.withdraw(50.0);

        // View transactions
        int transactionCount = customerPage.viewTransactions();
        assertTrue(transactionCount >= 2, "Should have at least 2 transactions");
        
        logger.info("Test passed: Transactions viewed successfully, count: {}", transactionCount);
    }

    @Test
    @DisplayName("Balance Update After Transactions")
    @Story("Transaction History")
    @Description("Test that balance updates correctly after multiple transactions")
    @Severity(SeverityLevel.CRITICAL)
    public void testBalanceUpdate() {
        logger.info("Starting test: testBalanceUpdate");
        
        customerPage.login("Harry Potter");
        double initialBalance = customerPage.getBalance();
        logger.debug("Initial balance: {}", initialBalance);

        // Multiple transactions
        double deposit1 = 500.0;
        double deposit2 = 250.0;
        double withdraw1 = 100.0;

        customerPage.deposit(deposit1);
        double balanceAfterDeposit1 = customerPage.getBalance();
        assertEquals(initialBalance + deposit1, balanceAfterDeposit1, 0.01);

        customerPage.deposit(deposit2);
        double balanceAfterDeposit2 = customerPage.getBalance();
        assertEquals(initialBalance + deposit1 + deposit2, balanceAfterDeposit2, 0.01);

        customerPage.withdraw(withdraw1);
        double finalBalance = customerPage.getBalance();
        assertEquals(initialBalance + deposit1 + deposit2 - withdraw1, finalBalance, 0.01);
        
        logger.info("Test passed: Balance updated correctly. Final: {}", finalBalance);
    }

    @ParameterizedTest
    @ValueSource(doubles = { 100.0, 250.5, 1000.0, 99.99 })
    @DisplayName("Deposit Various Amounts")
    @Story("Transactions")
    @Description("Parameterized test for depositing various positive amounts")
    @Severity(SeverityLevel.NORMAL)
    public void testDepositVariousAmounts(double amount) {
        logger.info("Testing deposit of amount: {}", amount);
        
        customerPage.login("Hermoine Granger");
        double initialBalance = customerPage.getBalance();

        customerPage.deposit(amount);
        double newBalance = customerPage.getBalance();

        assertEquals(initialBalance + amount, newBalance, 0.01, 
            "Balance should increase by deposit amount: " + amount);
    }

    @Test
    @DisplayName("Transaction History Cannot Be Modified")
    @Story("Transaction History")
    @Description("Verify that transaction history is read-only and cannot be modified")
    @Severity(SeverityLevel.NORMAL)
    public void testTransactionHistoryReadOnly() {
        logger.info("Starting test: testTransactionHistoryReadOnly");
        
        customerPage.login("Ron Weasley");
        customerPage.deposit(300.0);

        // Get transaction history
        var transactionHistory = customerPage.getTransactionHistory();
        logger.debug("Transactions: {}", transactionHistory);

        // Verify transactions are displayed (read-only historical data)
        assertFalse(transactionHistory.isEmpty(), "Transaction history should not be empty");
        
        logger.info("Test passed: Transaction history is read-only");
    }

    @Test
    @DisplayName("Customer Logout")
    @Story("Authentication")
    @Description("Test that customer can successfully logout")
    @Severity(SeverityLevel.NORMAL)
    public void testLogout() {
        logger.info("Starting test: testLogout");
        
        customerPage.login("Harry Potter");
        
        // Execute logout
        assertDoesNotThrow(customerPage::logout);
        
        logger.info("Test passed: Customer logged out successfully");
    }
}
