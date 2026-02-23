package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AddCustomerPage - Manager page for adding new customers
 */
public class AddCustomerPage {
    
    private static final Logger logger = LoggerFactory.getLogger(AddCustomerPage.class);
    private WebDriver driver;
    
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
    
    public AddCustomerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void clickAddCustomerTab() {
        logger.info("Clicking Add Customer tab");
        addCustomerTab.click();
    }
    
    public void enterFirstName(String firstName) {
        logger.info("Entering first name: {}", firstName);
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }
    
    public void enterLastName(String lastName) {
        logger.info("Entering last name: {}", lastName);
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }
    
    public void enterPostalCode(String postalCode) {
        logger.info("Entering postal code: {}", postalCode);
        postalCodeInput.clear();
        postalCodeInput.sendKeys(postalCode);
    }
    
    public void clickAddButton() {
        logger.info("Clicking Add button");
        addButton.click();
    }
    
    public void addCustomer(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        clickAddButton();
    }
}
