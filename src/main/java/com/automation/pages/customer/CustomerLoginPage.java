package com.automation.pages.customer;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CustomerLoginPage - Page Object for customer login functionality
 */
public class CustomerLoginPage {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomerLoginPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    // Web Elements
    @FindBy(id="userSelect")
    private WebElement customerDropdown;
    
    @FindBy(xpath = "//button[contains(text(), 'Login')]")
    private WebElement loginButton;
    
    /**
     * Constructor for CustomerLoginPage.
     * Initializes the WebDriver, WebDriverWait, and PageFactory elements.
     *
     * @param driver The WebDriver instance to be used by this page.
     */
    public CustomerLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Selects a customer from the dropdown menu.
     * Waits for the dropdown to be clickable, clicks it, and then selects the option matching the provided customer name.
     *
     * @param customerName The name of the customer to select.
     */
    public void selectCustomer(String customerName) {
        logger.info("Selecting customer: {}", customerName);
        wait.until(ExpectedConditions.elementToBeClickable(customerDropdown));
        customerDropdown.click();
        WebElement option = driver.findElement(By.xpath("//option[text()='" + customerName + "']"));
        wait.until(ExpectedConditions.elementToBeClickable(option));
        option.click();
    }
    
    /**
     * Clicks the login button.
     * Waits for the login button to be clickable before performing the click action.
     */
    public void clickLogin() {
        logger.info("Clicking login button");
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }
    
    /**
     * Performs the complete login process for a specific customer.
     * This method combines selecting the customer and clicking the login button.
     *
     * @param customerName The name of the customer to log in as.
     */
    public void loginAsCustomer(String customerName) {
        selectCustomer(customerName);
        clickLogin();
    }
}
