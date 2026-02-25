package com.automation.pages.manager;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OpenAccountPage - Manager page for opening new accounts
 */
public class OpenAccountPage {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenAccountPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    @FindBy(xpath = "//button[contains(text(), 'Open Account')]")
    private WebElement openAccountTab;
    
    @FindBy(id = "userSelect")
    private WebElement customerDropdown;
    
    @FindBy(id = "currency")
    private WebElement currencyDropdown;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement processButton;
    
    /**
     * Constructor for OpenAccountPage.
     * Initializes the WebDriver, WebDriverWait, and PageFactory elements.
     *
     * @param driver The WebDriver instance to be used by this page.
     */
    public OpenAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Clicks the 'Open Account' tab to navigate to the open account section.
     * Waits for the tab to be clickable before performing the action.
     */
    public void clickOpenAccountTab() {
        logger.info("Clicking Open Account tab");
        wait.until(ExpectedConditions.elementToBeClickable(openAccountTab));
        openAccountTab.click();
    }
    
    /**
     * Selects a customer from the customer dropdown menu.
     * Waits for the dropdown to be clickable and then selects the customer by visible text.
     *
     * @param customerName The name of the customer to select.
     */
    public void selectCustomer(String customerName) {
        logger.info("Selecting customer: {}", customerName);
        wait.until(ExpectedConditions.elementToBeClickable(customerDropdown));
        Select select = new Select(customerDropdown);
        select.selectByVisibleText(customerName);
    }
    
    /**
     * Selects a currency from the currency dropdown menu.
     * Waits for the dropdown to be clickable and then selects the currency by visible text.
     *
     * @param currency The currency to select (e.g., "Dollar", "Pound", "Rupee").
     */
    public void selectCurrency(String currency) {
        logger.info("Selecting currency: {}", currency);
        wait.until(ExpectedConditions.elementToBeClickable(currencyDropdown));
        Select select = new Select(currencyDropdown);
        select.selectByVisibleText(currency);
    }
    
    /**
     * Clicks the 'Process' button to submit the open account form.
     * Waits for the button to be clickable before performing the action.
     */
    public void clickProcess() {
        logger.info("Clicking Process button");
        wait.until(ExpectedConditions.elementToBeClickable(processButton));
        processButton.click();
    }
    
    /**
     * A comprehensive method to create a new account for a customer.
     * It navigates to the open account tab, selects the customer and currency, and then processes the request.
     *
     * @param customer The name of the customer.
     * @param currency The currency for the new account.
     */
    public void createAccount(String customer, String currency) {
        clickOpenAccountTab();
        selectCustomer(customer);
        selectCurrency(currency);
        clickProcess();
    }
}
