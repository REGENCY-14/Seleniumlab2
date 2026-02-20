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

/**
 * ManagerPage - Page Object Model for Bank Manager operations
 * Encapsulates all locators and interactions for manager-specific features
 * 
 * Features:
 * - Add Customer (names must be alphabetic, postal code numeric)
 * - Create Account for Customer
 * - Delete Account
 * - Uses explicit waits for reliability
 */
public class ManagerPage {
    private static final Logger logger = LogManager.getLogger(ManagerPage.class);
    private WebDriver driver;
    private WebDriverWait wait;

    // Manager interface buttons
    @FindBy(xpath = "//button[contains(text(), 'Add Customer')]")
    private WebElement addCustomerButton;

    @FindBy(xpath = "//button[contains(text(), 'Create Account')]")
    private WebElement createAccountButton;

    @FindBy(xpath = "//button[contains(text(), 'Delete Account')]")
    private WebElement deleteAccountButton;

    // Add Customer form elements
    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@placeholder='Post Code']")
    private WebElement postCodeInput;

    @FindBy(xpath = "//button[contains(text(), 'Add Customer')][@type='submit']")
    private WebElement submitAddCustomerButton;

    // Create Account form elements
    @FindBy(xpath = "//select[@ng-model='currencySelected']")
    private WebElement currencyDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Process')]")
    private WebElement processButton;

    // Customer selection dropdown
    @FindBy(xpath = "//select[@ng-model='accountNumber']")
    private WebElement accountNumberDropdown;

    // Customers table
    @FindBy(xpath = "//table[@class='table table-striped']")
    private WebElement customersTable;

    /**
     * Constructor - Initialize ManagerPage with WebDriver
     * Initializes PageFactory for element annotation processing
     * 
     * @param driver WebDriver instance
     */
    public ManagerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        logger.info("ManagerPage initialized");
    }

    /**
     * Click on Add Customer button to open the form
     */
    public void clickAddCustomerButton() {
        logger.info("Clicking Add Customer button");
        wait.until(ExpectedConditions.elementToBeClickable(addCustomerButton)).click();
    }

    /**
     * Add a new customer with validation
     * First name and last name must be alphabetic
     * Postal code must be numeric
     * 
     * @param firstName Customer first name (alphabetic)
     * @param lastName Customer last name (alphabetic)
     * @param postalCode Customer postal code (numeric)
     */
    public void addCustomer(String firstName, String lastName, String postalCode) {
        logger.info("Adding customer: {} {}, Postal Code: {}", firstName, lastName, postalCode);
        
        // Validate inputs
        validateAlphabetic(firstName, "First Name");
        validateAlphabetic(lastName, "Last Name");
        validateNumeric(postalCode, "Postal Code");

        clickAddCustomerButton();

        // Fill form
        WebElement firstNameField = wait.until(ExpectedConditions.visibilityOf(firstNameInput));
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
        logger.debug("Entered first name: {}", firstName);

        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
        logger.debug("Entered last name: {}", lastName);

        postCodeInput.clear();
        postCodeInput.sendKeys(postalCode);
        logger.debug("Entered postal code: {}", postalCode);

        // Submit form
        wait.until(ExpectedConditions.elementToBeClickable(submitAddCustomerButton)).click();
        logger.info("Customer added successfully: {} {}", firstName, lastName);
    }

    /**
     * Click on Create Account button
     */
    public void clickCreateAccountButton() {
        logger.info("Clicking Create Account button");
        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton)).click();
    }

    /**
     * Create a new account for an existing customer
     * 
     * @param customerName Name of the customer
     * @param currency Currency type for the account
     */
    public void createAccount(String customerName, String currency) {
        logger.info("Creating account for customer: {}, Currency: {}", customerName, currency);
        
        clickCreateAccountButton();

        // Select customer
        WebElement accountDropdown = wait.until(ExpectedConditions.visibilityOf(accountNumberDropdown));
        selectFromDropdown(accountDropdown, customerName);
        logger.debug("Selected customer: {}", customerName);

        // Select currency
        selectFromDropdown(currencyDropdown, currency);
        logger.debug("Selected currency: {}", currency);

        // Click process button
        wait.until(ExpectedConditions.elementToBeClickable(processButton)).click();
        logger.info("Account created successfully for customer: {}", customerName);
    }

    /**
     * Click on Delete Account button
     */
    public void clickDeleteAccountButton() {
        logger.info("Clicking Delete Account button");
        wait.until(ExpectedConditions.elementToBeClickable(deleteAccountButton)).click();
    }

    /**
     * Delete an account for a customer
     * 
     * @param customerName Name of the customer whose account to delete
     */
    public void deleteAccount(String customerName) {
        logger.info("Deleting account for customer: {}", customerName);
        
        clickDeleteAccountButton();

        // Select customer from dropdown
        WebElement accountDropdown = wait.until(ExpectedConditions.visibilityOf(accountNumberDropdown));
        selectFromDropdown(accountDropdown, customerName);
        logger.debug("Selected customer for deletion: {}", customerName);

        // Click process/delete button
        wait.until(ExpectedConditions.elementToBeClickable(processButton)).click();
        logger.info("Account deleted successfully for customer: {}", customerName);
    }

    /**
     * Helper method to select value from dropdown
     * 
     * @param dropdown WebElement dropdown
     * @param value Value to select
     */
    private void selectFromDropdown(WebElement dropdown, String value) {
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(dropdown);
        select.selectByVisibleText(value);
    }

    /**
     * Validate that a string contains only alphabetic characters
     * 
     * @param value String to validate
     * @param fieldName Name of the field for error messages
     * @throws IllegalArgumentException if value is not alphabetic
     */
    private void validateAlphabetic(String value, String fieldName) {
        if (!value.matches("[a-zA-Z ]+")) {
            logger.error("{} contains non-alphabetic characters: {}", fieldName, value);
            throw new IllegalArgumentException(fieldName + " must contain only alphabetic characters");
        }
    }

    /**
     * Validate that a string contains only numeric characters
     * 
     * @param value String to validate
     * @param fieldName Name of the field for error messages
     * @throws IllegalArgumentException if value is not numeric
     */
    private void validateNumeric(String value, String fieldName) {
        if (!value.matches("\\d+")) {
            logger.error("{} contains non-numeric characters: {}", fieldName, value);
            throw new IllegalArgumentException(fieldName + " must contain only numeric characters");
        }
    }

    /**
     * Check if customer exists in the customers table
     * 
     * @param customerName Name to search for
     * @return true if customer exists, false otherwise
     */
    public boolean isCustomerExists(String customerName) {
        try {
            WebElement table = wait.until(ExpectedConditions.visibilityOf(customersTable));
            String tableText = table.getText();
            boolean exists = tableText.contains(customerName);
            logger.info("Customer '{}' exists in table: {}", customerName, exists);
            return exists;
        } catch (Exception e) {
            logger.warn("Error checking if customer exists: {}", e.getMessage());
            return false;
        }
    }
}
