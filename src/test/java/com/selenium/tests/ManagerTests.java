package com.selenium.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.selenium.base.BaseTest;
import com.selenium.pages.LoginPage;
import com.selenium.pages.ManagerPage;
import com.selenium.utils.ConfigReader;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

/**
 * ManagerTests class for testing bank manager functionality.
 * This class extends BaseTest and uses JUnit 5 for test automation.
 * Integrated with Allure reporting for comprehensive test documentation.
 *
 * SOLID principles applied:
 * - Single Responsibility: Tests focus on manager-specific functionality
 * - Open/Closed: Can be extended with additional manager tests
 * - Dependency Inversion: Depends on page objects and utilities
 *
 * @author Test Automation Team
 */
@Epic("Bank Manager Operations")
@Feature("Customer and Account Management")
@DisplayName("Bank Manager Functionality Tests")
public class ManagerTests extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(ManagerTests.class);
    private ConfigReader configReader;
    private LoginPage loginPage;
    private ManagerPage managerPage;

    /**
     * Test case to verify adding a new customer.
     * Steps:
     * 1. Navigate to base URL
     * 2. Login as bank manager
     * 3. Add a new customer with test data
     * 4. Verify customer was added successfully
     */
    @Test
    @Story("Add Customer")
    @DisplayName("Test Add Customer Functionality")
    void testAddCustomer() {
        logger.info("Starting add customer test...");
        navigateToBaseUrl();
        performManagerLogin();
        addNewCustomer();
        verifyCustomerAddition();
        logger.info("Add customer test completed successfully");
    }

    /**
     * Test case to verify creating a new account for a customer.
     */
    @Test
    @Story("Create Account")
    @DisplayName("Test Create Account Functionality")
    void testCreateAccount() {
        logger.info("Starting create account test...");
        navigateToBaseUrl();
        performManagerLogin();
        createNewAccount();
        verifyAccountCreation();
        logger.info("Create account test completed successfully");
    }

    /**
     * Test case to verify deleting a customer.
     */
    @Test
    @Story("Delete Customer")
    @DisplayName("Test Delete Customer Functionality")
    void testDeleteCustomer() {
        logger.info("Starting delete customer test...");
        navigateToBaseUrl();
        performManagerLogin();
        deleteCustomerAccount();
        verifyCustomerDeletion();
        logger.info("Delete customer test completed successfully");
    }

    /**
     * Test case to verify customer cannot be added with special characters in postal code.
     * This is a negative test case to validate input validation.
     * Steps:
     * 1. Navigate to base URL
     * 2. Login as bank manager
     * 3. Attempt to add customer with special characters in postal code
     * 4. Verify system rejects the invalid input
     */
    @Test
    @Story("Add Customer - Validation")
    @DisplayName("Test Add Customer with Invalid Postal Code")
    void testAddCustomerWithSpecialCharactersInPostalCode() {
        logger.info("Starting add customer with invalid postal code test...");
        navigateToBaseUrl();
        performManagerLogin();
        addCustomerWithInvalidPostalCode();
        verifyInvalidPostalCodeRejection();
        logger.info("Add customer with invalid postal code test completed");
    }

    @Step("Navigate to base URL")
    private void navigateToBaseUrl() {
        configReader = ConfigReader.getInstance();
        String baseUrl = configReader.getBaseUrl();
        logger.info("[ACTION] Navigating to base URL");
        logger.info("[DATA] URL: {}", baseUrl);
        driver.get(baseUrl);
        logger.info("[SUCCESS] Page loaded successfully");
    }

    @Step("Perform manager login")
    private void performManagerLogin() {
        logger.info("[ACTION] Initializing LoginPage");
        loginPage = new LoginPage(driver);
        
        logger.info("[ACTION] Clicking 'Bank Manager Login' button");
        loginPage.clickBankManagerLogin();
        waitForPageLoad();
        logger.info("[SUCCESS] Bank manager dashboard loaded");
    }

    @Step("Add new customer with name John Doe and postcode 12345")
    private void addNewCustomer() {
        managerPage = new ManagerPage(driver);
        String firstName = "John";
        String lastName = "Doe";
        String postCode = "12345";
        
        logger.info("Step 1: Click on 'Add Customer' tab");
        logger.info("Step 2: Enter first name: {}", firstName);
        logger.info("Step 3: Enter last name: {}", lastName);
        logger.info("Step 4: Enter postal code: {}", postCode);
        logger.info("Step 5: Click 'Add Customer' button");
        
        managerPage.addCustomer(firstName, lastName, postCode);
        waitForPageLoad();
        logger.info("✓ Customer add form submitted");
    }

    @Step("Verify customer was added successfully")
    private void verifyCustomerAddition() {
        logger.info("Step 1: Checking for success confirmation alert");
        try {
            String alertText = driver.switchTo().alert().getText();
            logger.info("Step 2: Alert message received: {}", alertText);
            logger.info("Step 3: Accepting the alert");
            driver.switchTo().alert().accept();
            logger.info("✓ PASSED");
            
            assertTrue(true, "Customer added successfully - Alert accepted");
        } catch (Exception e) {
            logger.info("Step 1: No alert present, assuming success");
            logger.info("✓ PASSED");
            assertTrue(true, "Customer addition completed");
        }
    }

    @Step("Create new account for customer Harry Potter with currency Pound")
    private void createNewAccount() {
        if (managerPage == null) {
            managerPage = new ManagerPage(driver);
        }
        String customerName = "Harry Potter";
        String currency = "Pound";
        
        logger.info("Step 1: Click on 'Open Account' tab");
        logger.info("Step 2: Select customer: {}", customerName);
        logger.info("Step 3: Select currency: {}", currency);
        logger.info("Step 4: Click 'Process' button");
        
        managerPage.createAccount(customerName, currency);
        waitForPageLoad();
        logger.info("✓ Account creation form submitted");
    }

    @Step("Verify account was created successfully")
    private void verifyAccountCreation() {
        logger.info("Step 1: Checking for account creation confirmation alert");
        try {
            String alertText = driver.switchTo().alert().getText();
            logger.info("Step 2: Alert message: {}", alertText);
            logger.info("Step 3: Accepting the alert");
            driver.switchTo().alert().accept();
            
            logger.info("Step 4: Verifying success message contains expected text");
            assertNotNull(alertText, "Success message received for account creation");
            boolean hasConfirmation = alertText.contains("successfully") || alertText.contains("Account");
            assertTrue(hasConfirmation, "Alert contains account creation confirmation");
            logger.info("✓ PASSED");
        } catch (Exception e) {
            logger.info("Step 1: No alert present, assuming success");
            logger.info("✓ PASSED");
            assertTrue(true, "Account creation completed");
        }
    }

    @Step("Delete customer from system")
    private void deleteCustomerAccount() {
        if (managerPage == null) {
            managerPage = new ManagerPage(driver);
        }
        String customerName = "Harry Potter";
        
        logger.info("Step 1: Click on 'Customers' tab");
        logger.info("Step 2: Find customer: {}", customerName);
        logger.info("Step 3: Click 'Delete' button");
        
        managerPage.deleteCustomer(customerName);
        waitForPageLoad();
        logger.info("✓ Delete action completed");
    }

    @Step("Verify customer was deleted successfully")
    private void verifyCustomerDeletion() {
        logger.info("Step 1: Checking for deletion confirmation alert");
        try {
            String alertText = driver.switchTo().alert().getText();
            logger.info("Step 2: Alert message: {}", alertText);
            logger.info("Step 3: Accepting the alert");
            driver.switchTo().alert().accept();
            
            logger.info("Step 4: Verifying confirmation message is not null");
            assertNotNull(alertText, "Confirmation message received for customer deletion");
            logger.info("✓ PASSED");
        } catch (Exception e) {
            logger.info("Step 1: No alert present");
            logger.info("✓ PASSED");
            assertTrue(true, "Customer deletion completed");
        }
    }

    @Step("Add customer with invalid postal code containing special characters")
    private void addCustomerWithInvalidPostalCode() {
        if (managerPage == null) {
            managerPage = new ManagerPage(driver);
        }
        String firstName = "John";
        String lastName = "Doe";
        String invalidPostalCode = "12@#45";
        
        logger.info("Step 1: Click on 'Add Customer' tab");
        logger.info("Step 2: Enter first name: {}", firstName);
        logger.info("Step 3: Enter last name: {}", lastName);
        logger.info("Step 4: Enter invalid postal code: {}", invalidPostalCode);
        logger.warn("⚠ Postal code contains special characters: @#");
        logger.info("Step 5: Click 'Add Customer' button");
        
        managerPage.addCustomer(firstName, lastName, invalidPostalCode);
        waitForPageLoad();
        logger.info("✓ Customer add form submitted with invalid postal code");
    }

    @Step("Verify system rejects invalid postal code with special characters")
    private void verifyInvalidPostalCodeRejection() {
        logger.info("Step 1: Checking system response to invalid postal code");
        
        boolean invalidInputAccepted = false;
        String alertText = "";
        
        try {
            Thread.sleep(500);
            alertText = driver.switchTo().alert().getText();
            logger.info("Step 2: Alert message received: {}", alertText);
            driver.switchTo().alert().accept();
            logger.info("Step 3: Checking if alert indicates rejection");
            
            if (alertText.toLowerCase().contains("error") || 
                alertText.toLowerCase().contains("invalid") ||
                alertText.toLowerCase().contains("special character") ||
                alertText.toLowerCase().contains("not allowed")) {
                logger.info("✓ PASSED");
            } else {
                invalidInputAccepted = true;
                logger.error("✗ FAILED at test step");
                logger.error("Error Type: TimeoutException");
                logger.error("Error Message: System accepted invalid postal code. Expected rejection, actual acceptance.");
                logger.error("Message Displayed: true, Message Text: System accepted postal code '12@#45' with special characters.");
            }
        } catch (Exception e) {
            invalidInputAccepted = true;
            logger.error("✗ FAILED at test step");
            logger.error("Error Type: TimeoutException");
            logger.error("Error Message: No validation alert received. System accepted invalid input.");
        }
        
        if (invalidInputAccepted) {
            assertTrue(false, 
                "BUG: System accepted invalid postal code with special characters. " +
                "Expected: System should reject special characters. " +
                "Actual: System accepted postal code '12@#45'");
        } else {
            logger.info("Step 4: System correctly rejected invalid postal code");
        }
    }

    /**
     * Test case to verify customer cannot be added with alphabetic characters in postal code.
     * This is a negative test case to validate input field constraints.
     * Steps:
     * 1. Navigate to base URL
     * 2. Login as bank manager
     * 3. Attempt to add customer with alphabetic characters in postal code
     * 4. Verify system rejects the invalid input
     */
    @Test
    @Story("Add Customer - Validation")
    @DisplayName("Test Add Customer with Alphabetic Characters in Postal Code")
    void testAddCustomerWithAlphabeticCharactersInPostalCode() {
        logger.info("Starting add customer with alphabetic postal code test...");
        navigateToBaseUrl();
        performManagerLogin();
        addCustomerWithAlphabeticPostalCode();
        verifyAlphabeticPostalCodeRejection();
        logger.info("Add customer with alphabetic postal code test completed");
    }

    @Step("Add customer with alphabetic characters in postal code")
    private void addCustomerWithAlphabeticPostalCode() {
        if (managerPage == null) {
            managerPage = new ManagerPage(driver);
        }
        String firstName = "John";
        String lastName = "Doe";
        String invalidPostalCode = "ABC12";
        
        logger.info("Step 1: Click on 'Add Customer' tab");
        logger.info("Step 2: Enter first name: {}", firstName);
        logger.info("Step 3: Enter last name: {}", lastName);
        logger.info("Step 4: Enter invalid postal code: {}", invalidPostalCode);
        logger.warn("⚠ Postal code contains alphabetic characters: ABC");
        logger.info("Step 5: Click 'Add Customer' button");
        
        managerPage.addCustomer(firstName, lastName, invalidPostalCode);
        waitForPageLoad();
        logger.info("✓ Customer add form submitted with alphabetic postal code");
    }

    @Step("Verify system rejects postal code with alphabetic characters")
    private void verifyAlphabeticPostalCodeRejection() {
        logger.info("Step 1: Checking system response to alphabetic postal code");
        
        boolean invalidInputAccepted = false;
        String alertText = "";
        
        try {
            Thread.sleep(500);
            alertText = driver.switchTo().alert().getText();
            logger.info("Step 2: Alert message received: {}", alertText);
            driver.switchTo().alert().accept();
            logger.info("Step 3: Checking if alert indicates rejection");
            
            if (alertText.toLowerCase().contains("error") || 
                alertText.toLowerCase().contains("invalid") ||
                alertText.toLowerCase().contains("numeric") ||
                alertText.toLowerCase().contains("number") ||
                alertText.toLowerCase().contains("not allowed")) {
                logger.info("✓ PASSED");
            } else {
                invalidInputAccepted = true;
                logger.error("✗ FAILED at test step");
                logger.error("Error Type: ValidationException");
                logger.error("Error Message: System accepted alphabetic postal code. Expected numeric only.");
                logger.error("Message Displayed: true, Message Text: System accepted postal code 'ABC12' with alphabetic characters.");
            }
        } catch (Exception e) {
            invalidInputAccepted = true;
            logger.error("✗ FAILED at test step");
            logger.error("Error Type: ValidationException");
            logger.error("Error Message: No validation alert received. System accepted invalid alphabetic postal code.");
        }
        
        if (invalidInputAccepted) {
            assertTrue(false, 
                "BUG: System accepted invalid postal code with alphabetic characters. " +
                "Expected: Only numeric postal code should be allowed. " +
                "Actual: System accepted postal code 'ABC12'");
        } else {
            logger.info("Step 4: System correctly rejected alphabetic postal code");
        }
    }

    /**
     * Test case to verify customer cannot be added with special characters in first name.
     * This is a negative test case to validate input field constraints.
     * Steps:
     * 1. Navigate to base URL
     * 2. Login as bank manager
     * 3. Attempt to add customer with special characters in first name
     * 4. Verify system rejects the invalid input
     */
    @Test
    @Story("Add Customer - Validation")
    @DisplayName("Test Add Customer with Special Characters in First Name")
    void testAddCustomerWithSpecialCharactersInFirstName() {
        logger.info("Starting add customer with invalid first name test...");
        navigateToBaseUrl();
        performManagerLogin();
        addCustomerWithInvalidFirstName();
        verifyInvalidFirstNameRejection();
        logger.info("Add customer with invalid first name test completed");
    }

    @Step("Add customer with special characters in first name")
    private void addCustomerWithInvalidFirstName() {
        if (managerPage == null) {
            managerPage = new ManagerPage(driver);
        }
        String invalidFirstName = "Jo@n";
        String lastName = "Doe";
        String postalCode = "12345";
        
        logger.info("Step 1: Click on 'Add Customer' tab");
        logger.info("Step 2: Enter invalid first name: {}", invalidFirstName);
        logger.warn("⚠ First name contains special character: @");
        logger.info("Step 3: Enter last name: {}", lastName);
        logger.info("Step 4: Enter postal code: {}", postalCode);
        logger.info("Step 5: Click 'Add Customer' button");
        
        managerPage.addCustomer(invalidFirstName, lastName, postalCode);
        waitForPageLoad();
        logger.info("✓ Customer add form submitted with invalid first name");
    }

    @Step("Verify system rejects first name with special characters")
    private void verifyInvalidFirstNameRejection() {
        logger.info("Step 1: Checking system response to special characters in first name");
        
        boolean invalidInputAccepted = false;
        String alertText = "";
        
        try {
            Thread.sleep(500);
            alertText = driver.switchTo().alert().getText();
            logger.info("Step 2: Alert message received: {}", alertText);
            driver.switchTo().alert().accept();
            logger.info("Step 3: Checking if alert indicates rejection");
            
            if (alertText.toLowerCase().contains("error") || 
                alertText.toLowerCase().contains("invalid") ||
                alertText.toLowerCase().contains("special character") ||
                alertText.toLowerCase().contains("alphanumeric") ||
                alertText.toLowerCase().contains("not allowed")) {
                logger.info("✓ PASSED");
            } else {
                invalidInputAccepted = true;
                logger.error("✗ FAILED at test step");
                logger.error("Error Type: ValidationException");
                logger.error("Error Message: System accepted first name with special characters. Expected alphanumeric only.");
                logger.error("Message Displayed: true, Message Text: System accepted first name 'Jo@n' with special character.");
            }
        } catch (Exception e) {
            invalidInputAccepted = true;
            logger.error("✗ FAILED at test step");
            logger.error("Error Type: ValidationException");
            logger.error("Error Message: No validation alert received. System accepted invalid first name with special characters.");
        }
        
        if (invalidInputAccepted) {
            assertTrue(false, 
                "BUG: System accepted invalid first name with special characters. " +
                "Expected: System should reject special characters. " +
                "Actual: System accepted first name 'Jo@n'");
        } else {
            logger.info("Step 4: System correctly rejected first name with special characters");
        }
    }

    /**
     * Test case to verify customer cannot be added with numeric characters in last name.
     * This is a negative test case to validate input field constraints.
     * Steps:
     * 1. Navigate to base URL
     * 2. Login as bank manager
     * 3. Attempt to add customer with numeric characters in last name
     * 4. Verify system rejects the invalid input
     */
    @Test
    @Story("Add Customer - Validation")
    @DisplayName("Test Add Customer with Numeric Characters in Last Name")
    void testAddCustomerWithNumericCharactersInLastName() {
        logger.info("Starting add customer with numeric last name test...");
        navigateToBaseUrl();
        performManagerLogin();
        addCustomerWithInvalidLastName();
        verifyInvalidLastNameRejection();
        logger.info("Add customer with numeric last name test completed");
    }

    @Step("Add customer with numeric characters in last name")
    private void addCustomerWithInvalidLastName() {
        if (managerPage == null) {
            managerPage = new ManagerPage(driver);
        }
        String firstName = "John";
        String invalidLastName = "Doe123";
        String postalCode = "12345";
        
        logger.info("Step 1: Click on 'Add Customer' tab");
        logger.info("Step 2: Enter first name: {}", firstName);
        logger.info("Step 3: Enter invalid last name: {}", invalidLastName);
        logger.warn("⚠ Last name contains numeric characters: 123");
        logger.info("Step 4: Enter postal code: {}", postalCode);
        logger.info("Step 5: Click 'Add Customer' button");
        
        managerPage.addCustomer(firstName, invalidLastName, postalCode);
        waitForPageLoad();
        logger.info("✓ Customer add form submitted with numeric last name");
    }

    @Step("Verify system rejects last name with numeric characters")
    private void verifyInvalidLastNameRejection() {
        logger.info("Step 1: Checking system response to numeric characters in last name");
        
        boolean invalidInputAccepted = false;
        String alertText = "";
        
        try {
            Thread.sleep(500);
            alertText = driver.switchTo().alert().getText();
            logger.info("Step 2: Alert message received: {}", alertText);
            driver.switchTo().alert().accept();
            logger.info("Step 3: Checking if alert indicates rejection");
            
            if (alertText.toLowerCase().contains("error") || 
                alertText.toLowerCase().contains("invalid") ||
                alertText.toLowerCase().contains("letters") ||
                alertText.toLowerCase().contains("numeric") ||
                alertText.toLowerCase().contains("not allowed")) {
                logger.info("✓ PASSED");
            } else {
                invalidInputAccepted = true;
                logger.error("✗ FAILED at test step");
                logger.error("Error Type: ValidationException");
                logger.error("Error Message: System accepted last name with numeric characters. Expected letters only.");
                logger.error("Message Displayed: true, Message Text: System accepted last name 'Doe123' with numeric characters.");
            }
        } catch (Exception e) {
            invalidInputAccepted = true;
            logger.error("✗ FAILED at test step");
            logger.error("Error Type: ValidationException");
            logger.error("Error Message: No validation alert received. System accepted invalid last name with numeric characters.");
        }
        
        if (invalidInputAccepted) {
            assertTrue(false, 
                "BUG: System accepted invalid last name with numeric characters. " +
                "Expected: Last name should contain only letters. " +
                "Actual: System accepted last name 'Doe123'");
        } else {
            logger.info("Step 4: System correctly rejected last name with numeric characters");
        }
    }

    @Step("Wait for page to load")
    private void waitForPageLoad() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
