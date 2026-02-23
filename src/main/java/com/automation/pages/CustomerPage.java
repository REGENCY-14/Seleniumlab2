package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CustomerPage - Legacy customer page aggregating multiple page objects
 * This page combines deposit, withdrawal, transactions, and dashboard functionality
 */
public class CustomerPage {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomerPage.class);
    private WebDriver driver;
    
    private DepositPage depositPage;
    private WithdrawalPage withdrawalPage;
    private TransactionsPage transactionsPage;
    private CustomerDashboardPage dashboardPage;
    
    public CustomerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.depositPage = new DepositPage(driver);
        this.withdrawalPage = new WithdrawalPage(driver);
        this.transactionsPage = new TransactionsPage(driver);
        this.dashboardPage = new CustomerDashboardPage(driver);
    }
    
    public void deposit(double amount) {
        depositPage.depositAmount(String.valueOf(amount));
    }
    
    public void withdraw(double amount) {
        withdrawalPage.withdrawAmount(String.valueOf(amount));
    }
    
    public String getBalance() {
        return dashboardPage.getBalance();
    }
    
    public void viewTransactions() {
        transactionsPage.clickTransactionsTab();
    }
    
    public int getTransactionCount() {
        return transactionsPage.getTransactionCount();
    }
}
