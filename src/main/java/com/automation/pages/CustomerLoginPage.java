package com.automation.pages;

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
    
    public CustomerLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    public void selectCustomer(String customerName) {
        logger.info("Selecting customer: {}", customerName);
        wait.until(ExpectedConditions.elementToBeClickable(customerDropdown));
        customerDropdown.click();
        WebElement option = driver.findElement(By.xpath("//option[text()='" + customerName + "']"));
        wait.until(ExpectedConditions.elementToBeClickable(option));
        option.click();
    }
    
    public void clickLogin() {
        logger.info("Clicking login button");
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }
    
    public void loginAsCustomer(String customerName) {
        selectCustomer(customerName);
        clickLogin();
    }
}
