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
    
    /**
     * Constructor for WithdrawalPage.
     * Initializes the WebDriver, WebDriverWait, and PageFactory elements.
     *
     * @param driver The WebDriver instance to be used by this page.
     */
    public WithdrawalPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Clicks the 'Withdrawl' tab to navigate to the withdrawal section.
     * Waits for the tab to be clickable before performing the action.
     */
    public void clickWithdrawTab() {
        logger.info("Clicking Withdraw tab");
        wait.until(ExpectedConditions.elementToBeClickable(withdrawTab));
        withdrawTab.click();
    }
    
    /**
     * Enters the specified amount into the amount input field.
     * Waits for the input field to be visible, clears it, and then enters the amount.
     *
     * @param amount The amount to be entered for withdrawal.
     */
    public void enterAmount(String amount) {
        logger.info("Entering withdrawal amount: {}", amount);
        wait.until(ExpectedConditions.visibilityOf(amountInput));
        amountInput.clear();
        amountInput.sendKeys(amount);
    }
    
    /**
     * Submits the withdrawal form.
     * Waits for the withdraw button to be clickable before clicking it.
     */
    public void submitWithdrawal() {
        logger.info("Submitting withdrawal");
        wait.until(ExpectedConditions.elementToBeClickable(withdrawButton));
        withdrawButton.click();
    }
    
    /**
     * A comprehensive method to perform a withdrawal.
     * It navigates to the withdrawal tab, enters the amount, and submits the withdrawal.
     *
     * @param amount The amount to be withdrawn.
     */
    public void withdrawAmount(String amount) {
        clickWithdrawTab();
        enterAmount(amount);
        submitWithdrawal();
    }
}
