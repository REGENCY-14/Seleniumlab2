package com.automation.pages.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automation.pages.customer.CustomerDashboardPage;
import com.automation.pages.customer.DepositPage;
import com.automation.pages.customer.TransactionsPage;
import com.automation.pages.customer.WithdrawalPage;

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
    
    /**
     * Constructor for CustomerPage.
     * Initializes the WebDriver and all associated page objects for customer actions.
     *
     * @param driver The WebDriver instance to be used.
     */
    public CustomerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.depositPage = new DepositPage(driver);
        this.withdrawalPage = new WithdrawalPage(driver);
        this.transactionsPage = new TransactionsPage(driver);
        this.dashboardPage = new CustomerDashboardPage(driver);
    }
    
    /**
     * Performs a deposit action for the customer.
     *
     * @param amount The amount to deposit.
     */
    public void deposit(double amount) {
        depositPage.depositAmount(String.valueOf(amount));
    }
    
    /**
     * Performs a withdrawal action for the customer.
     *
     * @param amount The amount to withdraw.
     */
    public void withdraw(double amount) {
        withdrawalPage.withdrawAmount(String.valueOf(amount));
    }
    
    /**
     * Retrieves the current account balance from the dashboard.
     *
     * @return The current balance as a String.
     */
    public String getBalance() {
        return dashboardPage.getBalance();
    }
    
    /**
     * Navigates to the transactions view.
     */
    public void viewTransactions() {
        transactionsPage.clickTransactionsTab();
    }
    
    /**
     * Retrieves the number of transactions listed.
     *
     * @return The total count of transactions.
     */
    public int getTransactionCount() {
        return transactionsPage.getTransactionCount();
    }
}
