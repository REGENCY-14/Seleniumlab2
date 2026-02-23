package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * TransactionsPage - Displays transaction history
 */
public class TransactionsPage {
    
    private static final Logger logger = LoggerFactory.getLogger(TransactionsPage.class);
    private WebDriver driver;
    
    @FindBy(xpath = "//button[contains(text(), 'Transactions')]")
    private WebElement transactionsTab;
    
    @FindBy(xpath = "//table//tbody//tr")
    private List<WebElement> transactionRows;
    
    public TransactionsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void clickTransactionsTab() {
        logger.info("Clicking Transactions tab");
        transactionsTab.click();
    }
    
    public int getTransactionCount() {
        logger.info("Getting transaction count");
        return transactionRows.size();
    }
    
    public List<WebElement> getTransactions() {
        logger.info("Retrieving all transactions");
        return transactionRows;
    }
    
    public boolean hasTransactions() {
        return getTransactionCount() > 0;
    }
}
