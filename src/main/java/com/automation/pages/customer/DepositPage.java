package com.automation.pages.customer;

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
    
    /**
     * Constructor for DepositPage.
     * Initializes the WebDriver, WebDriverWait, and PageFactory elements.
     *
     * @param driver The WebDriver instance to be used by this page.
     */
    public DepositPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Clicks the 'Deposit' tab to navigate to the deposit section.
     * Waits for the tab to be clickable before performing the action.
     */
    public void clickDepositTab() {
        logger.info("Clicking Deposit tab");
        wait.until(ExpectedConditions.elementToBeClickable(depositTab));
        depositTab.click();
    }
    
    /**
     * Enters the specified amount into the amount input field.
     * Waits for the input field to be visible, clears it, and then enters the amount.
     *
     * @param amount The amount to be entered for deposit.
     */
    public void enterAmount(String amount) {
        logger.info("Entering deposit amount: {}", amount);
        wait.until(ExpectedConditions.visibilityOf(amountInput));
        amountInput.clear();
        amountInput.sendKeys(amount);
    }
    
    /**
     * Submits the deposit form.
     * Waits for the deposit button to be clickable before clicking it.
     */
    public void submitDeposit() {
        logger.info("Submitting deposit");
        wait.until(ExpectedConditions.elementToBeClickable(depositButton));
        depositButton.click();
    }
    
    /**
     * A comprehensive method to perform a deposit.
     * It navigates to the deposit tab, enters the amount, and submits the deposit.
     *
     * @param amount The amount to be deposited.
     */
    public void depositAmount(String amount) {
        clickDepositTab();
        enterAmount(amount);
        submitDeposit();
    }
}
