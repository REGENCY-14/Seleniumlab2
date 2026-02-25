package com.automation.pages.customer;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TransactionsPage - Displays transaction history
 */
public class TransactionsPage {
    
    private static final Logger logger = LoggerFactory.getLogger(TransactionsPage.class);
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    @FindBy(xpath = "//button[contains(text(), 'Transactions')]")
    private WebElement transactionsTab;
    
    @FindBy(xpath = "//table//tbody//tr")
    private List<WebElement> transactionRows;
    
    /**
     * Constructor for TransactionsPage.
     * Initializes the WebDriver, WebDriverWait, and PageFactory elements.
     *
     * @param driver The WebDriver instance to be used by this page.
     */
    public TransactionsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Clicks the 'Transactions' tab to navigate to the transaction history.
     * Waits for the tab to be clickable before performing the action.
     */
    public void clickTransactionsTab() {
        logger.info("Clicking Transactions tab");
        wait.until(ExpectedConditions.elementToBeClickable(transactionsTab));
        transactionsTab.click();
    }
    
    /**
     * Retrieves the number of transactions currently displayed in the table.
     *
     * @return The count of transaction rows.
     */
    public int getTransactionCount() {
        logger.info("Getting transaction count");
        return transactionRows.size();
    }
    
    /**
     * Retrieves the list of transaction row elements.
     *
     * @return A list of WebElements representing the transaction rows.
     */
    public List<WebElement> getTransactions() {
        logger.info("Retrieving all transactions");
        return transactionRows;
    }
    
    /**
     * Checks if there are any transactions present.
     *
     * @return true if the transaction count is greater than 0, false otherwise.
     */
    public boolean hasTransactions() {
        return getTransactionCount() > 0;
    }
}
