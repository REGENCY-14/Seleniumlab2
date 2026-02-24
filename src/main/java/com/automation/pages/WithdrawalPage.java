package com.automation.pages;

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
 * WithdrawalPage - Handles withdrawal operations
 */
public class WithdrawalPage {
    
    private static final Logger logger = LoggerFactory.getLogger(WithdrawalPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    @FindBy(xpath = "//button[contains(text(), 'Withdrawl')]")
    private WebElement withdrawTab;
    
    @FindBy(xpath = "//input[@ng-model='amount']")
    private WebElement amountInput;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement withdrawButton;
    
    public WithdrawalPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    public void clickWithdrawTab() {
        logger.info("Clicking Withdraw tab");
        wait.until(ExpectedConditions.elementToBeClickable(withdrawTab));
        withdrawTab.click();
    }
    
    public void enterAmount(String amount) {
        logger.info("Entering withdrawal amount: {}", amount);
        wait.until(ExpectedConditions.visibilityOf(amountInput));
        amountInput.clear();
        amountInput.sendKeys(amount);
    }
    
    public void submitWithdrawal() {
        logger.info("Submitting withdrawal");
        wait.until(ExpectedConditions.elementToBeClickable(withdrawButton));
        withdrawButton.click();
    }
    
    public void withdrawAmount(String amount) {
        clickWithdrawTab();
        enterAmount(amount);
        submitWithdrawal();
    }
}
