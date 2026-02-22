package com.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

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

    private final WebDriver driver;

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
    @FindBy(xpath = "//strong[contains(text(), 'Balance')]/following-sibling::*[1]")
    private WebElement balanceText;

    /**
     * Constructor to initialize CustomerPage with WebDriver.
     * Uses PageFactory to initialize all @FindBy annotated elements.
     *
     * @param driver the WebDriver instance
     */
    public CustomerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Login with the specified customer name.
     * Selects the customer from dropdown and clicks login button.
     *
     * @param customerName the name of the customer to login as
     */
    public void loginCustomer(String customerName) {
        Select customerSelect = new Select(customerDropdown);
        customerSelect.selectByVisibleText(customerName);
        loginButton.click();
    }

    /**
     * Deposit the specified amount into the customer account.
     *
     * @param amount the amount to deposit
     */
    public void deposit(double amount) {
        depositTab.click();
        depositAmountInput.clear();
        depositAmountInput.sendKeys(String.valueOf(amount));
        depositSubmitButton.click();
    }

    /**
     * Withdraw the specified amount from the customer account.
     *
     * @param amount the amount to withdraw
     */
    public void withdraw(double amount) {
        withdrawTab.click();
        withdrawAmountInput.clear();
        withdrawAmountInput.sendKeys(String.valueOf(amount));
        withdrawSubmitButton.click();
    }

    /**
     * Get the current account balance.
     *
     * @return the current balance as a String
     */
    public String getBalance() {
        return balanceText.getText();
    }

    /**
     * View the transaction history by clicking the transactions tab.
     */
    public void viewTransactions() {
        transactionsTab.click();
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
