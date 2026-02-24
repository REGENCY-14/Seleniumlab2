package com.automation.pages;

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
    
    public TransactionsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }
    
    public void clickTransactionsTab() {
        logger.info("Clicking Transactions tab");
        wait.until(ExpectedConditions.elementToBeClickable(transactionsTab));
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
