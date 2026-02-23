package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CustomerDashboardPage - Customer dashboard after login
 */
public class CustomerDashboardPage {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomerDashboardPage.class);
    private WebDriver driver;
    
    @FindBy(xpath = "//strong[@class='ng-binding'][2]")
    private WebElement balanceDisplay;
    
    @FindBy(xpath = "//button[contains(text(), 'Logout')]")
    private WebElement logoutButton;
    
    public CustomerDashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public String getBalance() {
        logger.info("Getting customer balance");
        return balanceDisplay.getText();
    }
    
    public void logout() {
        logger.info("Clicking logout button");
        logoutButton.click();
    }
}
