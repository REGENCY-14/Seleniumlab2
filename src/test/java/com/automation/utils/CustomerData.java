package com.automation.utils;

/**
 * CustomerData - Test data for customer operations
 */
public class CustomerData {
    
    public static final String TEST_CUSTOMER_NAME = "Harry Potter";
    public static final String NEW_CUSTOMER_FIRST_NAME = "John";
    public static final String NEW_CUSTOMER_LAST_NAME = "Doe";
    public static final String NEW_CUSTOMER_POSTAL_CODE = "12345";
    
    // Invalid data for validation tests
    public static final String INVALID_POSTAL_SPECIAL_CHARS = "12@#45";
    public static final String INVALID_POSTAL_ALPHABETIC = "ABC12";
    public static final String INVALID_FIRST_NAME = "Jo@n";
    public static final String INVALID_LAST_NAME = "Doe123";
    
    // Deposit/Withdrawal amounts
    public static final double DEPOSIT_AMOUNT = 500.0;
    public static final double WITHDRAWAL_AMOUNT = 100.0;
    public static final double TEST_TRANSACTION_AMOUNT = 250.0;
}
