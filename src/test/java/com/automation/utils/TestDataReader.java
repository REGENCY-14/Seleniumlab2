package com.automation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

/**
 * TestDataReader - Utility to read test data from testdata.json
 */
public class TestDataReader {
    
    private static final Logger logger = LoggerFactory.getLogger(TestDataReader.class);
    private static TestDataReader instance;
    private JsonNode testData;
    
    private TestDataReader() {
        try {
            String testDataPath = "src/main/resources/testdata.json";
            ObjectMapper objectMapper = new ObjectMapper();
            testData = objectMapper.readTree(new File(testDataPath));
            logger.info("Test data loaded successfully from testdata.json");
        } catch (IOException e) {
            logger.error("Failed to load test data", e);
            testData = null;
        }
    }
    
    public static TestDataReader getInstance() {
        if (instance == null) {
            instance = new TestDataReader();
        }
        return instance;
    }
    
    // Valid Customer Data
    public String getTestCustomerName() {
        return getStringValue("validCustomers.testCustomer");
    }
    
    public String getNewCustomerFirstName() {
        return getStringValue("validCustomers.newCustomerFirstName");
    }
    
    public String getNewCustomerLastName() {
        return getStringValue("validCustomers.newCustomerLastName");
    }
    
    public String getNewCustomerPostalCode() {
        return getStringValue("validCustomers.newCustomerPostalCode");
    }
    
    // Validation Data
    public String getInvalidPostalCodeWithSpecialChars() {
        return getStringValue("validationData.invalidPostalCodeSpecialChars");
    }
    
    public String getInvalidPostalCodeWithAlphabetic() {
        return getStringValue("validationData.invalidPostalCodeAlphabetic");
    }
    
    public String getInvalidFirstName() {
        return getStringValue("validationData.invalidFirstName");
    }
    
    public String getInvalidLastName() {
        return getStringValue("validationData.invalidLastName");
    }
    
    // Transaction Data
    public double getDepositAmount() {
        return getDoubleValue("transactions.depositAmount");
    }
    
    public double getWithdrawalAmount() {
        return getDoubleValue("transactions.withdrawalAmount");
    }
    
    public double getTestTransactionAmount() {
        return getDoubleValue("transactions.testTransactionAmount");
    }
    
    // Customer List
    public String[] getCustomerList() {
        return getStringArray("customerList");
    }
    
    public String getDeleteCustomerName() {
        return getStringValue("deleteCustomer.name");
    }
    
    // Account Data
    public String[] getAvailableCurrencies() {
        return getStringArray("accounts.availableCurrencies");
    }
    
    public String getDefaultCurrency() {
        return getStringValue("accounts.defaultCurrency");
    }
    
    public String[] getProtectedAccounts() {
        return getStringArray("protectedAccounts");
    }
    
    // Helper methods
    private String getStringValue(String path) {
        try {
            JsonNode node = testData.at("/" + path.replace(".", "/"));
            if (node.isMissingNode()) {
                logger.warn("Test data not found for path: {}", path);
                return null;
            }
            return node.asText();
        } catch (Exception e) {
            logger.error("Error reading test data for path: {}", path, e);
            return null;
        }
    }
    
    private double getDoubleValue(String path) {
        try {
            JsonNode node = testData.at("/" + path.replace(".", "/"));
            if (node.isMissingNode()) {
                logger.warn("Test data not found for path: {}", path);
                return 0.0;
            }
            return node.asDouble();
        } catch (Exception e) {
            logger.error("Error reading test data for path: {}", path, e);
            return 0.0;
        }
    }
    
    private String[] getStringArray(String path) {
        try {
            JsonNode node = testData.at("/" + path.replace(".", "/"));
            if (node.isMissingNode() || !node.isArray()) {
                logger.warn("Test data array not found for path: {}", path);
                return new String[]{};
            }
            String[] array = new String[node.size()];
            for (int i = 0; i < node.size(); i++) {
                array[i] = node.get(i).asText();
            }
            return array;
        } catch (Exception e) {
            logger.error("Error reading test data array for path: {}", path, e);
            return new String[]{};
        }
    }
}
