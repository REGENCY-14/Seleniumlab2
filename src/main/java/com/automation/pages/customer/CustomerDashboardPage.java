package com.automation.pages.customer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
    
    /**
     * Constructor for CustomerDashboardPage.
     * Initializes the WebDriver and PageFactory elements.
     *
     * @param driver The WebDriver instance to be used by this page.
     */
    public CustomerDashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Retrieves the current balance displayed on the dashboard.
     *
     * @return The balance as a String.
     */
    public String getBalance() {
        logger.info("Getting customer balance");
        return balanceDisplay.getText();
    }
    
    /**
     * Logs out the current customer.
     * Clicks the 'Logout' button to end the session.
     */
    public void logout() {
        logger.info("Clicking logout button");
        logoutButton.click();
    }
}
