package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DepositPage - Handles deposit operations
 */
public class DepositPage {
    
    private static final Logger logger = LoggerFactory.getLogger(DepositPage.class);
    private WebDriver driver;
    
    @FindBy(xpath = "//button[contains(text(), 'Deposit')]")
    private WebElement depositTab;
    
    @FindBy(xpath = "//input[@ng-model='amount']")
    private WebElement amountInput;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement depositButton;
    
    public DepositPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void clickDepositTab() {
        logger.info("Clicking Deposit tab");
        depositTab.click();
    }
    
    public void enterAmount(String amount) {
        logger.info("Entering deposit amount: {}", amount);
        amountInput.clear();
        amountInput.sendKeys(amount);
    }
    
    public void submitDeposit() {
        logger.info("Submitting deposit");
        depositButton.click();
    }
    
    public void depositAmount(String amount) {
        clickDepositTab();
        enterAmount(amount);
        submitDeposit();
    }
}
