package com.automation.pages;

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
    
    public OpenAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    public void clickOpenAccountTab() {
        logger.info("Clicking Open Account tab");
        wait.until(ExpectedConditions.elementToBeClickable(openAccountTab));
        openAccountTab.click();
    }
    
    public void selectCustomer(String customerName) {
        logger.info("Selecting customer: {}", customerName);
        wait.until(ExpectedConditions.elementToBeClickable(customerDropdown));
        Select select = new Select(customerDropdown);
        select.selectByVisibleText(customerName);
    }
    
    public void selectCurrency(String currency) {
        logger.info("Selecting currency: {}", currency);
        wait.until(ExpectedConditions.elementToBeClickable(currencyDropdown));
        Select select = new Select(currencyDropdown);
        select.selectByVisibleText(currency);
    }
    
    public void clickProcess() {
        logger.info("Clicking Process button");
        wait.until(ExpectedConditions.elementToBeClickable(processButton));
        processButton.click();
    }
    
    public void createAccount(String customer, String currency) {
        clickOpenAccountTab();
        selectCustomer(customer);
        selectCurrency(currency);
        clickProcess();
    }
}
