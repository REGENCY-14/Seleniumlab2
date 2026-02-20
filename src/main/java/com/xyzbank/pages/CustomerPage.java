package com.xyzbank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;
import java.util.List;

/**
 * CustomerPage - Page Object Model for Customer operations
 * Encapsulates all locators and interactions for customer-specific features
 * 
 * Features:
 * - Login to customer account
 * - Deposit money (positive values only)
 * - Withdraw money (positive values and sufficient balance)
 * - View transactions
 * - Check balance
 * - Uses explicit waits for reliability
 */
public class CustomerPage {
    private static final Logger logger = LogManager.getLogger(CustomerPage.class);
    private WebDriver driver;
    private WebDriverWait wait;

    // Login elements
    @FindBy(xpath = "//button[contains(text(), 'Customer Login')]")
    private WebElement customerLoginButton;

    @FindBy(xpath = "//select[@ng-model='currencySelected']")
    private WebElement loginCustomerDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Login')]")
    private WebElement loginButton;

    // Customer account elements
    @FindBy(xpath = "//div[@ng-show='accountNumber']")
    private WebElement accountNumberDisplay;

    @FindBy(xpath = "//span[@ng-show='balance']")
    private WebElement balanceDisplay;

    @FindBy(xpath = "//button[contains(text(), 'Deposit')]")
    private WebElement depositButton;

    @FindBy(xpath = "//button[contains(text(), 'Withdraw')]")
    private WebElement withdrawButton;

    @FindBy(xpath = "//button[contains(text(), 'Transactions')]")
    private WebElement transactionsButton;

    @FindBy(xpath = "//button[contains(text(), 'Logout')]")
    private WebElement logoutButton;

    // Deposit/Withdraw form
    @FindBy(xpath = "//input[@placeholder='amount']")
    private WebElement amountInput;

    @FindBy(xpath = "//button[@type='submit'][contains(text(), 'Deposit') or contains(text(), 'Withdraw')]")
    private WebElement submitButton;

    // Transactions table
    @FindBy(xpath = "//table[@class='table table-striped']")
    private WebElement transactionsTable;

    @FindBy(xpath = "//table//tr")
    private List<WebElement> transactionRows;

    // Alert message
    @FindBy(xpath = "//strong[contains(text(), 'Deposit') or contains(text(), 'Withdraw') or contains(text(), 'insufficient')]")
    private WebElement alertMessage;

    /**
     * Constructor - Initialize CustomerPage with WebDriver
     * 
     * @param driver WebDriver instance
     */
    public CustomerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        logger.info("CustomerPage initialized");
    }

    /**
     * Click on Customer Login button
     */
    public void clickCustomerLoginButton() {
        logger.info("Clicking Customer Login button");
        wait.until(ExpectedConditions.elementToBeClickable(customerLoginButton)).click();
    }

    /**
     * Login as a customer
     * 
     * @param customerName Name of the customer to login as
     */
    public void login(String customerName) {
        logger.info("Logging in as customer: {}", customerName);
        
        clickCustomerLoginButton();

        // Select customer from dropdown
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOf(loginCustomerDropdown));
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(dropdown);
        select.selectByVisibleText(customerName);
        logger.debug("Selected customer: {}", customerName);

        // Click login button
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        
        // Wait for account information to appear
        wait.until(ExpectedConditions.visibilityOf(accountNumberDisplay));
        logger.info("Successfully logged in as customer: {}", customerName);
    }

    /**
     * Click on Deposit button
     */
    public void clickDepositButton() {
        logger.info("Clicking Deposit button");
        wait.until(ExpectedConditions.elementToBeClickable(depositButton)).click();
    }

    /**
     * Deposit money to customer account
     * Amount must be a positive value
     * 
     * @param amount Amount to deposit (must be positive)
     * @throws IllegalArgumentException if amount is not positive
     */
    public void deposit(double amount) {
        logger.info("Depositing amount: {}", amount);
        
        // Validate amount is positive
        if (amount <= 0) {
            logger.error("Invalid deposit amount: {}", amount);
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        clickDepositButton();

        // Enter amount
        WebElement amountField = wait.until(ExpectedConditions.visibilityOf(amountInput));
        amountField.clear();
        amountField.sendKeys(String.valueOf(amount));
        logger.debug("Entered deposit amount: {}", amount);

        // Submit
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        
        // Wait for success message
        waitForAlertMessage();
        logger.info("Deposit of {} completed successfully", amount);
    }

    /**
     * Click on Withdraw button
     */
    public void clickWithdrawButton() {
        logger.info("Clicking Withdraw button");
        wait.until(ExpectedConditions.elementToBeClickable(withdrawButton)).click();
    }

    /**
     * Withdraw money from customer account
     * Amount must be positive and account must have sufficient balance
     * 
     * @param amount Amount to withdraw (must be positive and <= balance)
     * @throws IllegalArgumentException if amount is not positive
     */
    public void withdraw(double amount) {
        logger.info("Withdrawing amount: {}", amount);
        
        // Validate amount is positive
        if (amount <= 0) {
            logger.error("Invalid withdrawal amount: {}", amount);
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        clickWithdrawButton();

        // Enter amount
        WebElement amountField = wait.until(ExpectedConditions.visibilityOf(amountInput));
        amountField.clear();
        amountField.sendKeys(String.valueOf(amount));
        logger.debug("Entered withdrawal amount: {}", amount);

        // Submit
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        
        // Wait for response (success or error)
        waitForAlertMessage();
        logger.info("Withdrawal of {} processed", amount);
    }

    /**
     * Get current account balance
     * 
     * @return Account balance as double
     */
    public double getBalance() {
        try {
            WebElement balance = wait.until(ExpectedConditions.visibilityOf(balanceDisplay));
            String balanceText = balance.getText();
            double balanceValue = Double.parseDouble(balanceText);
            logger.info("Current balance: {}", balanceValue);
            return balanceValue;
        } catch (Exception e) {
            logger.error("Error getting balance: {}", e.getMessage());
            return 0.0;
        }
    }

    /**
     * Click on Transactions button to view transaction history
     */
    public void clickTransactionsButton() {
        logger.info("Clicking Transactions button");
        wait.until(ExpectedConditions.elementToBeClickable(transactionsButton)).click();
    }

    /**
     * View transactions for the logged-in customer
     * 
     * @return Number of transactions in history
     */
    public int viewTransactions() {
        logger.info("Viewing transactions");
        clickTransactionsButton();

        try {
            wait.until(ExpectedConditions.visibilityOf(transactionsTable));
            int transactionCount = transactionRows.size();
            logger.info("Found {} transactions", transactionCount);
            return transactionCount;
        } catch (Exception e) {
            logger.warn("Error viewing transactions: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Get transaction details from the transaction table
     * 
     * @return List of transaction strings
     */
    public List<String> getTransactionHistory() {
        logger.info("Getting transaction history");
        try {
            WebElement table = wait.until(ExpectedConditions.visibilityOf(transactionsTable));
            String[] tableLines = table.getText().split("\n");
            return java.util.Arrays.asList(tableLines);
        } catch (Exception e) {
            logger.error("Error getting transaction history: {}", e.getMessage());
            return java.util.Collections.emptyList();
        }
    }

    /**
     * Click on Logout button
     */
    public void clickLogoutButton() {
        logger.info("Clicking Logout button");
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }

    /**
     * Logout from customer account
     */
    public void logout() {
        logger.info("Logging out from customer account");
        clickLogoutButton();
        logger.info("Successfully logged out");
    }

    /**
     * Wait for alert message to appear (success/error)
     */
    private void waitForAlertMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(alertMessage));
            logger.debug("Alert message displayed");
        } catch (Exception e) {
            logger.warn("No alert message appeared: {}", e.getMessage());
        }
    }

    /**
     * Check if withdrawal failed due to insufficient balance
     * 
     * @return true if insufficient balance message is shown, false otherwise
     */
    public boolean hasInsufficientBalanceError() {
        try {
            String alertText = alertMessage.getText();
            boolean hasError = alertText.toLowerCase().contains("insufficient");
            logger.info("Insufficient balance error detected: {}", hasError);
            return hasError;
        } catch (Exception e) {
            logger.debug("No insufficient balance error");
            return false;
        }
    }
}
