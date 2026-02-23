package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LoginPage - Main login page
 */
public class LoginPage {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    private WebDriver driver;
    
    @FindBy(xpath = "//button[contains(text(), 'Customer Login')]")
    private WebElement customerLoginButton;
    
    @FindBy(xpath = "//button[contains(text(), 'Bank Manager Login')]")
    private WebElement bankManagerLoginButton;
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void clickCustomerLogin() {
        logger.info("Clicking Customer Login");
        customerLoginButton.click();
    }
    
    public void clickBankManagerLogin() {
        logger.info("Clicking Bank Manager Login");
        bankManagerLoginButton.click();
    }
}
