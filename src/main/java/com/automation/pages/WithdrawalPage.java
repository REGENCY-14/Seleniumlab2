package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WithdrawalPage - Handles withdrawal operations
 */
public class WithdrawalPage {
    
    private static final Logger logger = LoggerFactory.getLogger(WithdrawalPage.class);
    private WebDriver driver;
    
    @FindBy(xpath = "//button[contains(text(), 'Withdraw')]")
    private WebElement withdrawTab;
    
    @FindBy(xpath = "//input[@ng-model='amount']")
    private WebElement amountInput;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement withdrawButton;
    
    public WithdrawalPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void clickWithdrawTab() {
        logger.info("Clicking Withdraw tab");
        withdrawTab.click();
    }
    
    public void enterAmount(String amount) {
        logger.info("Entering withdrawal amount: {}", amount);
        amountInput.clear();
        amountInput.sendKeys(amount);
    }
    
    public void submitWithdrawal() {
        logger.info("Submitting withdrawal");
        withdrawButton.click();
    }
    
    public void withdrawAmount(String amount) {
        clickWithdrawTab();
        enterAmount(amount);
        submitWithdrawal();
    }
}
