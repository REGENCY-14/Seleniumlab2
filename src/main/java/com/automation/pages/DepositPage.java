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
 * DepositPage - Handles deposit operations
 */
public class DepositPage {
    
    private static final Logger logger = LoggerFactory.getLogger(DepositPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    @FindBy(xpath = "//button[contains(text(), 'Deposit')]")
    private WebElement depositTab;
    
    @FindBy(xpath = "//input[@ng-model='amount']")
    private WebElement amountInput;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement depositButton;
    
    public DepositPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    public void clickDepositTab() {
        logger.info("Clicking Deposit tab");
        wait.until(ExpectedConditions.elementToBeClickable(depositTab));
        depositTab.click();
    }
    
    public void enterAmount(String amount) {
        logger.info("Entering deposit amount: {}", amount);
        wait.until(ExpectedConditions.visibilityOf(amountInput));
        amountInput.clear();
        amountInput.sendKeys(amount);
    }
    
    public void submitDeposit() {
        logger.info("Submitting deposit");
        wait.until(ExpectedConditions.elementToBeClickable(depositButton));
        depositButton.click();
    }
    
    public void depositAmount(String amount) {
        clickDepositTab();
        enterAmount(amount);
        submitDeposit();
    }
}
