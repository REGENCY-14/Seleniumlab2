package com.automation.pages.manager;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AddCustomerPage - Manager page for adding new customers
 */
public class AddCustomerPage {
    
    private static final Logger logger = LoggerFactory.getLogger(AddCustomerPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    @FindBy(xpath = "//button[contains(text(), 'Add Customer')]")
    private WebElement addCustomerTab;
    
    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElement firstNameInput;
    
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement lastNameInput;
    
    @FindBy(xpath = "//input[@placeholder='Post Code']")
    private WebElement postalCodeInput;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement addButton;
    
    /**
     * Constructor for AddCustomerPage.
     * Initializes the WebDriver, WebDriverWait, and PageFactory elements.
     *
     * @param driver The WebDriver instance to be used by this page.
     */
    public AddCustomerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Clicks the 'Add Customer' tab to navigate to the add customer form.
     * Waits for the tab to be clickable before performing the action.
     */
    public void clickAddCustomerTab() {
        logger.info("Clicking Add Customer tab");
        wait.until(ExpectedConditions.elementToBeClickable(addCustomerTab));
        addCustomerTab.click();
    }
    
    /**
     * Enters the first name of the customer into the first name input field.
     * Waits for the input field to be visible, clears it, and then enters the first name.
     *
     * @param firstName The first name of the customer.
     */
    public void enterFirstName(String firstName) {
        logger.info("Entering first name: {}", firstName);
        wait.until(ExpectedConditions.visibilityOf(firstNameInput));
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }
    
    /**
     * Enters the last name of the customer into the last name input field.
     * Waits for the input field to be visible, clears it, and then enters the last name.
     *
     * @param lastName The last name of the customer.
     */
    public void enterLastName(String lastName) {
        logger.info("Entering last name: {}", lastName);
        wait.until(ExpectedConditions.visibilityOf(lastNameInput));
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }
    
    /**
     * Enters the postal code of the customer into the postal code input field.
     * Waits for the input field to be visible, clears it, and then enters the postal code.
     *
     * @param postalCode The postal code of the customer.
     */
    public void enterPostalCode(String postalCode) {
        logger.info("Entering postal code: {}", postalCode);
        wait.until(ExpectedConditions.visibilityOf(postalCodeInput));
        postalCodeInput.clear();
        postalCodeInput.sendKeys(postalCode);
    }
    
    /**
     * Clicks the 'Add' button to submit the add customer form.
     * Waits for the button to be clickable before performing the action.
     */
    public void clickAddButton() {
        logger.info("Clicking Add button");
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        addButton.click();
    }
    
    /**
     * A comprehensive method to add a new customer.
     * It navigates to the add customer tab, enters the first name, last name, and postal code, and then submits the form.
     *
     * @param firstName  The first name of the customer.
     * @param lastName   The last name of the customer.
     * @param postalCode The postal code of the customer.
     */
    public void addCustomer(String firstName, String lastName, String postalCode) {
        clickAddCustomerTab();
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        clickAddButton();
    }
}
