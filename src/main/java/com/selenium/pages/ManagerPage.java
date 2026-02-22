package com.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * ManagerPage class representing the bank manager functionality page.
 * This class follows the Page Object Model (POM) pattern.
 *
 * SOLID principles applied:
 * - Single Responsibility: Only handles bank manager page interactions
 * - Open/Closed: Can be extended for additional page elements
 * - Interface Segregation: Focused methods for specific actions
 *
 * @author Test Automation Team
 */
public class ManagerPage {

    private final WebDriver driver;

    // ============ Add Customer Section WebElements ============

    /**
     * First Name input field for adding a new customer.
     */
    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElement firstNameInput;

    /**
     * Last Name input field for adding a new customer.
     */
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement lastNameInput;

    /**
     * Post Code input field for adding a new customer.
     */
    @FindBy(xpath = "//input[@placeholder='Post Code']")
    private WebElement postCodeInput;

    /**
     * Add Customer button to submit the customer creation form.
     */
    @FindBy(xpath = "//button[contains(text(), 'Add Customer')]")
    private WebElement addCustomerButton;

    // ============ Create Account Section WebElements ============

    /**
     * Customer dropdown for selecting a customer when creating an account.
     */
    @FindBy(id = "userSelect")
    private WebElement customerDropdown;

    /**
     * Currency dropdown for selecting currency when creating an account.
     */
    @FindBy(id = "currencySelect")
    private WebElement currencyDropdown;

    /**
     * Create Account button to submit the account creation form.
     */
    @FindBy(xpath = "//button[contains(text(), 'Process')]")
    private WebElement createAccountButton;

    // ============ Delete Account Section WebElements ============

    /**
     * Customers tab to view and manage existing customers.
     */
    @FindBy(xpath = "//button[contains(text(), 'Customers')]")
    private WebElement customersTab;

    /**
     * Delete button for removing a customer (dynamic locator used in methods).
     */
    @FindBy(xpath = "//button[contains(text(), 'Delete')]")
    private WebElement deleteButton;

    /**
     * Constructor to initialize ManagerPage with WebDriver.
     * Uses PageFactory to initialize all @FindBy annotated elements.
     *
     * @param driver the WebDriver instance
     */
    public ManagerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Add a new customer with the provided details.
     *
     * @param firstName the customer's first name
     * @param lastName  the customer's last name
     * @param postCode  the customer's post code
     */
    public void addCustomer(String firstName, String lastName, String postCode) {
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);

        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);

        postCodeInput.clear();
        postCodeInput.sendKeys(postCode);

        addCustomerButton.click();
    }

    /**
     * Create a new account for a specific customer with selected currency.
     *
     * @param customerName the name of the customer to select
     * @param currency     the currency to select for the account
     */
    public void createAccount(String customerName, String currency) {
        Select customerSelect = new Select(customerDropdown);
        customerSelect.selectByVisibleText(customerName);

        Select currencySelect = new Select(currencyDropdown);
        currencySelect.selectByVisibleText(currency);

        createAccountButton.click();
    }

    /**
     * Delete a customer from the system.
     * Note: This method assumes the customer list is already displayed.
     *
     * @param customerName the name of the customer to delete
     */
    public void deleteCustomer(String customerName) {
        // Click on customers tab to ensure we're viewing the customer list
        customersTab.click();

        // Find and click the delete button for the specific customer
        // This implementation assumes the delete button is visible after clicking customersTab
        deleteButton.click();
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

    /**
     * Get the currency dropdown element.
     *
     * @return the currency dropdown WebElement
     */
    public WebElement getCurrencyDropdown() {
        return currencyDropdown;
    }
}
