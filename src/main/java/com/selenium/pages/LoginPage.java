package com.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * LoginPage class representing the login page of the application.
 * This class follows the Page Object Model (POM) pattern.
 *
 * SOLID principles applied:
 * - Single Responsibility: Only handles login page interactions
 * - Open/Closed: Can be extended for additional page elements
 * - Liskov Substitution: Can be used with any BasePage subclass
 *
 * @author Test Automation Team
 */
public class LoginPage {

    private final WebDriver driver;

    /**
     * WebElement for Bank Manager Login button.
     * Located by button text content.
     */
    @FindBy(xpath = "//button[contains(text(), 'Bank Manager Login')]")
    private WebElement bankManagerLoginButton;

    /**
     * WebElement for Customer Login button.
     * Located by button text content.
     */
    @FindBy(xpath = "//button[contains(text(), 'Customer Login')]")
    private WebElement customerLoginButton;

    /**
     * Constructor to initialize LoginPage with WebDriver.
     * Uses PageFactory to initialize all @FindBy annotated elements.
     *
     * @param driver the WebDriver instance
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Click on Bank Manager Login button.
     * Navigates to the Bank Manager login page/form.
     */
    public void clickBankManagerLogin() {
        bankManagerLoginButton.click();
    }

    /**
     * Click on Customer Login button.
     * Navigates to the Customer login page/form.
     */
    public void clickCustomerLogin() {
        customerLoginButton.click();
    }

    /**
     * Get the WebDriver instance.
     *
     * @return the WebDriver instance
     */
    protected WebDriver getDriver() {
        return driver;
    }
}
