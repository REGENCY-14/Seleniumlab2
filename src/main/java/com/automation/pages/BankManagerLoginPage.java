package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BankManagerLoginPage - Page Object for bank manager login
 */
public class BankManagerLoginPage {
    
    private static final Logger logger = LoggerFactory.getLogger(BankManagerLoginPage.class);
    private WebDriver driver;
    
    @FindBy(xpath = "//button[contains(text(), 'Bank Manager Login')]")
    private WebElement bankManagerLoginButton;
    
    public BankManagerLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void clickBankManagerLogin() {
        logger.info("Clicking Bank Manager Login button");
        bankManagerLoginButton.click();
    }
}
