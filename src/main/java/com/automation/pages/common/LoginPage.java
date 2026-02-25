package com.automation.pages.common;

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
 * LoginPage - Main login page
 */
public class LoginPage {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    @FindBy(xpath = "//button[contains(text(), 'Customer Login')]")
    private WebElement customerLoginButton;
    
    @FindBy(xpath = "//button[contains(text(), 'Bank Manager Login')]")
    private WebElement bankManagerLoginButton;
    
    /**
     * Constructor for LoginPage.
     * Initializes the WebDriver, WebDriverWait, and PageFactory elements.
     *
     * @param driver The WebDriver instance to be used by this page.
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Clicks the 'Customer Login' button.
     * Waits for the button to be clickable before performing the click.
     */
    public void clickCustomerLogin() {
        logger.info("Clicking Customer Login");
        wait.until(ExpectedConditions.elementToBeClickable(customerLoginButton));
        customerLoginButton.click();
    }
    
    /**
     * Clicks the 'Bank Manager Login' button.
     * Waits for the button to be clickable before performing the click.
     */
    public void clickBankManagerLogin() {
        logger.info("Clicking Bank Manager Login");
        wait.until(ExpectedConditions.elementToBeClickable(bankManagerLoginButton));
        bankManagerLoginButton.click();
    }
}
