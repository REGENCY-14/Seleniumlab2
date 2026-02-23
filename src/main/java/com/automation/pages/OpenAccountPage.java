package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OpenAccountPage - Manager page for opening new accounts
 */
public class OpenAccountPage {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenAccountPage.class);
    private WebDriver driver;
    
    @FindBy(xpath = "//button[contains(text(), 'Open Account')]")
    private WebElement openAccountTab;
    
    @FindBy(id = "userSelect")
    private WebElement customerDropdown;
    
    @FindBy(id = "currency")
    private WebElement currencyDropdown;
    
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement processButton;
    
    public OpenAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void clickOpenAccountTab() {
        logger.info("Clicking Open Account tab");
        openAccountTab.click();
    }
    
    public void selectCustomer(String customerName) {
        logger.info("Selecting customer: {}", customerName);
        Select select = new Select(customerDropdown);
        select.selectByVisibleText(customerName);
    }
    
    public void selectCurrency(String currency) {
        logger.info("Selecting currency: {}", currency);
        Select select = new Select(currencyDropdown);
        select.selectByVisibleText(currency);
    }
    
    public void clickProcess() {
        logger.info("Clicking Process button");
        processButton.click();
    }
    
    public void createAccount(String customer, String currency) {
        selectCustomer(customer);
        selectCurrency(currency);
        clickProcess();
    }
}
