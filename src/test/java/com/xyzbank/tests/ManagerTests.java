package com.xyzbank.tests;

import com.xyzbank.pages.ManagerPage;
import com.xyzbank.tests.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ManagerTests - Comprehensive test suite for Bank Manager operations
 * Tests manager-specific functionality:
 * - Add Customer with valid data
 * - Add Customer with invalid data
 * - Create Account
 * - Delete Account
 * 
 * Uses JUnit 5 and Allure reporting annotations
 */
@Feature("Bank Manager Functions")
@DisplayName("Manager Test Suite")
public class ManagerTests extends BaseTest {
    
    private ManagerPage managerPage;
    private static final String BANK_APP_URL = "https://www.globalsqa.com/angularjs-protractor/bankingproject/#/login";

    /**
     * Setup before each manager test
     * Initializes ManagerPage
     */
    @BeforeEach
    public void setUpManager() {
        super.setUp();
        logger.info("Initializing ManagerPage");
        navigateTo(BANK_APP_URL);
        managerPage = new ManagerPage(driver);
    }

    @Test
    @DisplayName("Add Customer with Valid Data")
    @Story("Customer Management")
    @Description("Test adding a new customer with valid alphabetic names and numeric postal code")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddCustomerValid() {
        logger.info("Starting test: testAddCustomerValid");
        
        String firstName = "John";
        String lastName = "Doe";
        String postalCode = "12345";

        // Execute
        assertDoesNotThrow(() -> managerPage.addCustomer(firstName, lastName, postalCode));
        
        // Verify - Customer should be added successfully
        boolean customerExists = managerPage.isCustomerExists(firstName + " " + lastName);
        assertTrue(customerExists, "Customer should be added to the database");
        
        logger.info("Test passed: Customer added successfully");
    }

    @Test
    @DisplayName("Add Customer with Invalid First Name")
    @Story("Customer Management")
    @Description("Test that adding customer with non-alphabetic first name throws error")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddCustomerInvalidFirstName() {
        logger.info("Starting test: testAddCustomerInvalidFirstName");
        
        String invalidFirstName = "John123";
        String lastName = "Doe";
        String postalCode = "12345";

        // Execute and verify - Should throw IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> managerPage.addCustomer(invalidFirstName, lastName, postalCode),
            "Should throw exception for non-alphabetic first name"
        );
        
        assertTrue(exception.getMessage().contains("alphabetic"));
        logger.info("Test passed: Exception thrown for invalid first name");
    }

    @Test
    @DisplayName("Add Customer with Invalid Postal Code")
    @Story("Customer Management")
    @Description("Test that adding customer with non-numeric postal code throws error")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddCustomerInvalidPostalCode() {
        logger.info("Starting test: testAddCustomerInvalidPostalCode");
        
        String firstName = "Jane";
        String lastName = "Smith";
        String invalidPostalCode = "ABC123";

        // Execute and verify - Should throw IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> managerPage.addCustomer(firstName, lastName, invalidPostalCode),
            "Should throw exception for non-numeric postal code"
        );
        
        assertTrue(exception.getMessage().contains("numeric"));
        logger.info("Test passed: Exception thrown for invalid postal code");
    }

    @ParameterizedTest
    @CsvSource({
        "Alice, Johnson, 54321",
        "Bob, Williams, 11111",
        "Carol, Brown, 99999"
    })
    @DisplayName("Add Multiple Customers with Valid Data")
    @Story("Customer Management")
    @Description("Parameterized test for adding multiple customers with different valid data")
    @Severity(SeverityLevel.NORMAL)
    public void testAddMultipleCustomers(String firstName, String lastName, String postalCode) {
        logger.info("Testing add customer with: {}, {}, {}", firstName, lastName, postalCode);
        
        // Execute
        assertDoesNotThrow(() -> managerPage.addCustomer(firstName, lastName, postalCode));
        
        // Verify
        boolean customerExists = managerPage.isCustomerExists(firstName + " " + lastName);
        assertTrue(customerExists, "Customer " + firstName + " should be added");
    }

    @Test
    @DisplayName("Create Account for Customer")
    @Story("Account Management")
    @Description("Test creating a new account for an existing customer")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateAccount() {
        logger.info("Starting test: testCreateAccount");
        
        // First, add a customer
        String firstName = "Tom";
        String lastName = "Hardy";
        String postalCode = "77777";
        
        managerPage.addCustomer(firstName, lastName, postalCode);
        String customerFullName = firstName + " " + lastName;
        assertTrue(managerPage.isCustomerExists(customerFullName));
        
        // Then create account
        assertDoesNotThrow(() -> managerPage.createAccount(customerFullName, "Dollar"));
        logger.info("Test passed: Account created successfully");
    }

    @Test
    @DisplayName("Delete Account for Customer")
    @Story("Account Management")
    @Description("Test deleting an account for an existing customer")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteAccount() {
        logger.info("Starting test: testDeleteAccount");
        
        // First, add a customer and create account
        String firstName = "Sarah";
        String lastName = "Connor";
        String postalCode = "88888";
        
        managerPage.addCustomer(firstName, lastName, postalCode);
        String customerFullName = firstName + " " + lastName;
        assertTrue(managerPage.isCustomerExists(customerFullName));
        
        managerPage.createAccount(customerFullName, "Pound");
        
        // Then delete the account
        assertDoesNotThrow(() -> managerPage.deleteAccount(customerFullName));
        logger.info("Test passed: Account deleted successfully");
    }
}
