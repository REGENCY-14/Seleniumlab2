package com.selenium.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CustomerPage class representing the customer functionality page.
 * This class follows the Page Object Model (POM) pattern.
 *
 * SOLID principles applied:
 * - Single Responsibility: Only handles customer page interactions
 * - Open/Closed: Can be extended for additional page elements
 * - Interface Segregation: Focused methods for specific customer actions
 *
 * @author Test Automation Team
 */
public class CustomerPage {

    private static final Logger logger = LoggerFactory.getLogger(CustomerPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;

    // ============ Login Section WebElements ============

    /**
     * Customer dropdown for selecting a customer to login.
     */
    @FindBy(id = "userSelect")
    private WebElement customerDropdown;

    /**
     * Login button to initiate customer login.
     */
    @FindBy(xpath = "//button[contains(text(), 'Login')]")
    private WebElement loginButton;

    // ============ Deposit Section WebElements ============

    /**
     * Deposit tab to access deposit functionality.
     */
    @FindBy(xpath = "//button[contains(text(), 'Deposit')]")
    private WebElement depositTab;

    /**
     * Deposit amount input field for entering deposit amount.
     */
    @FindBy(xpath = "//input[@placeholder='amount']")
    private WebElement depositAmountInput;

    /**
     * Deposit submit button to submit deposit transaction.
     */
    @FindBy(xpath = "//form//button[contains(text(), 'Deposit')]")
    private WebElement depositSubmitButton;

    // ============ Withdraw Section WebElements ============

    /**
     * Withdraw tab to access withdraw functionality.
     */
    @FindBy(xpath = "//button[contains(text(), 'Withdraw')]")
    private WebElement withdrawTab;

    /**
     * Withdraw amount input field for entering withdrawal amount.
     */
    @FindBy(xpath = "//input[@placeholder='amount']")
    private WebElement withdrawAmountInput;

    /**
     * Withdraw submit button to submit withdrawal transaction.
     */
    @FindBy(xpath = "//form//button[contains(text(), 'Withdraw')]")
    private WebElement withdrawSubmitButton;

    // ============ Transactions Section WebElements ============

    /**
     * Transactions tab to view transaction history.
     */
    @FindBy(xpath = "//button[contains(text(), 'Transactions')]")
    private WebElement transactionsTab;

    /**
     * Balance text element displaying current account balance.
     */
    @FindBy(xpath = "//strong[@class='ng-binding'][2]")
    private WebElement balanceText;

    /**
     * Constructor to initialize CustomerPage with WebDriver.
     * Uses PageFactory to initialize all @FindBy annotated elements.
     *
     * @param driver the WebDriver instance
     */
    public CustomerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
        logger.debug("CustomerPage initialized");
    }

    /**
     * Login with the specified customer name.
     * Selects the customer from dropdown and clicks login button.
     *
     * @param customerName the name of the customer to login as
     */
    public void loginCustomer(String customerName) {
        logger.info("Logging in as customer: {}", customerName);
        wait.until(ExpectedConditions.visibilityOf(customerDropdown));
        Select customerSelect = new Select(customerDropdown);
        customerSelect.selectByVisibleText(customerName);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
        logger.info("Customer login successful");
    }

    /**
     * Deposit the specified amount into the customer account.
     *
     * @param amount the amount to deposit
     */
    public void deposit(double amount) {
        logger.info("Depositing amount: ${}", amount);
        wait.until(ExpectedConditions.elementToBeClickable(depositTab));
        depositTab.click();
        
        // Wait a bit for Angular to update the form
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        
        wait.until(ExpectedConditions.visibilityOf(depositAmountInput));
        depositAmountInput.clear();
        depositAmountInput.sendKeys(String.valueOf(amount));
        wait.until(ExpectedConditions.elementToBeClickable(depositSubmitButton));
        depositSubmitButton.click();
        logger.info("Deposit transaction submitted");
    }

    /**
     * Withdraw the specified amount from the customer account.
     *
     * @param amount the amount to withdraw
     */
    public void withdraw(double amount) {
        logger.info("Withdrawing amount: ${}", amount);
        wait.until(ExpectedConditions.elementToBeClickable(withdrawTab));
        withdrawTab.click();
        
        // Wait a bit for Angular to update the form
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        
        wait.until(ExpectedConditions.visibilityOf(withdrawAmountInput));
        withdrawAmountInput.clear();
        withdrawAmountInput.sendKeys(String.valueOf(amount));
        wait.until(ExpectedConditions.elementToBeClickable(withdrawSubmitButton));
        withdrawSubmitButton.click();
        logger.info("Withdrawal transaction submitted");
    }

    /**
     * Get the current account balance.
     *
     * @return the current balance as a String
     */
    public String getBalance() {
        wait.until(ExpectedConditions.visibilityOf(balanceText));
        String balance = balanceText.getText();
        logger.info("Current balance: {}", balance);
        return balance;
    }

    /**
     * View the transaction history by clicking the transactions tab.
     */
    public void viewTransactions() {
        logger.info("Viewing transaction history");
        wait.until(ExpectedConditions.elementToBeClickable(transactionsTab));
        transactionsTab.click();
        logger.debug("Transactions tab clicked");
    }

    /**
     * Get the WebDriver instance.
     *
     * @return the WebDriver instance
     */
    protected WebDriver getDriver() {
        return driver;
    }

    /**
     * Get the customer dropdown element.
     *
     * @return the customer dropdown WebElement
     */
    public WebElement getCustomerDropdown() {
        return customerDropdown;
    }
}
