package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CustomerLoginPage - Page Object for customer login functionality
 */
public class CustomerLoginPage {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomerLoginPage.class);
    private WebDriver driver;
    
    // Web Elements
    @FindBy(name = "userSelect")
    private WebElement customerDropdown;
    
    @FindBy(xpath = "//button[contains(text(), 'Login')]")
    private WebElement loginButton;
    
    public CustomerLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void selectCustomer(String customerName) {
        logger.info("Selecting customer: {}", customerName);
        customerDropdown.click();
        driver.findElement(By.xpath("//option[text()='" + customerName + "']")).click();
    }
    
    public void clickLogin() {
        logger.info("Clicking login button");
        loginButton.click();
    }
    
    public void loginAsCustomer(String customerName) {
        selectCustomer(customerName);
        clickLogin();
    }
}
