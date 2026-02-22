package com.selenium.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * TestDataReader utility class for reading test data from JSON files.
 * Uses Jackson library for JSON processing.
 *
 * This class follows the SOLID principles:
 * - Single Responsibility: Only manages test data reading from JSON
 * - Open/Closed: Can be extended for additional data sources
 * - Dependency Inversion: Depends on Jackson abstraction (ObjectMapper)
 *
 * @author Test Automation Team
 */
public class TestDataReader {

    private static final String TEST_DATA_FILE = "testdata.json";
    private static final String CUSTOMERS_NODE = "customers";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static JsonNode testData;
    private static TestDataReader instance;
    private int customerIndex = 0;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private TestDataReader() {
        loadTestData();
    }

    /**
     * Get singleton instance of TestDataReader.
     *
     * @return TestDataReader singleton instance
     */
    public static synchronized TestDataReader getInstance() {
        if (instance == null) {
            instance = new TestDataReader();
        }
        return instance;
    }

    /**
     * Load test data from testdata.json file.
     *
     * @throws RuntimeException if testdata.json file is not found
     */
    private void loadTestData() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(TEST_DATA_FILE)) {
            if (input == null) {
                throw new RuntimeException("testdata.json file not found in resources folder");
            }
            testData = objectMapper.readTree(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading testdata.json file: " + e.getMessage(), e);
        }
    }

    /**
     * Get the customer's first name from test data.
     * Retrieves from the first customer in the JSON array.
     *
     * @return customer's first name
     * @throws RuntimeException if firstName is not found
     */
    public String getCustomerFirstName() {
        return getCustomerFirstName(customerIndex);
    }

    /**
     * Get the customer's first name from test data by index.
     *
     * @param index the customer index in the JSON array
     * @return customer's first name
     * @throws RuntimeException if firstName is not found
     */
    public String getCustomerFirstName(int index) {
        JsonNode customer = getCustomer(index);
        if (!customer.has("firstName") || customer.get("firstName").isNull()) {
            throw new RuntimeException("firstName not found in testdata.json for customer at index: " + index);
        }
        return customer.get("firstName").asText();
    }

    /**
     * Get the customer's last name from test data.
     * Retrieves from the first customer in the JSON array.
     *
     * @return customer's last name
     * @throws RuntimeException if lastName is not found
     */
    public String getCustomerLastName() {
        return getCustomerLastName(customerIndex);
    }

    /**
     * Get the customer's last name from test data by index.
     *
     * @param index the customer index in the JSON array
     * @return customer's last name
     * @throws RuntimeException if lastName is not found
     */
    public String getCustomerLastName(int index) {
        JsonNode customer = getCustomer(index);
        if (!customer.has("lastName") || customer.get("lastName").isNull()) {
            throw new RuntimeException("lastName not found in testdata.json for customer at index: " + index);
        }
        return customer.get("lastName").asText();
    }

    /**
     * Get the customer's post code from test data.
     * Retrieves from the first customer in the JSON array.
     *
     * @return customer's post code
     * @throws RuntimeException if postCode is not found
     */
    public String getPostCode() {
        return getPostCode(customerIndex);
    }

    /**
     * Get the customer's post code from test data by index.
     *
     * @param index the customer index in the JSON array
     * @return customer's post code
     * @throws RuntimeException if postCode is not found
     */
    public String getPostCode(int index) {
        JsonNode customer = getCustomer(index);
        if (!customer.has("postCode") || customer.get("postCode").isNull()) {
            throw new RuntimeException("postCode not found in testdata.json for customer at index: " + index);
        }
        return customer.get("postCode").asText();
    }

    /**
     * Get full customer name (firstName + lastName).
     *
     * @return full customer name
     */
    public String getCustomerFullName() {
        return getCustomerFullName(customerIndex);
    }

    /**
     * Get full customer name by index.
     *
     * @param index the customer index in the JSON array
     * @return full customer name
     */
    public String getCustomerFullName(int index) {
        return getCustomerFirstName(index) + " " + getCustomerLastName(index);
    }

    /**
     * Set the current customer index for data retrieval.
     *
     * @param index the customer index in the JSON array
     */
    public void setCustomerIndex(int index) {
        if (index < 0 || index >= getCustomerCount()) {
            throw new IllegalArgumentException("Invalid customer index: " + index);
        }
        this.customerIndex = index;
    }

    /**
     * Get the total number of customers in test data.
     *
     * @return number of customers
     */
    public int getCustomerCount() {
        if (testData != null && testData.has(CUSTOMERS_NODE)) {
            return testData.get(CUSTOMERS_NODE).size();
        }
        return 0;
    }

    /**
     * Get a customer node by index from test data.
     *
     * @param index the customer index in the JSON array
     * @return the customer JsonNode
     * @throws RuntimeException if customer not found at index
     */
    private JsonNode getCustomer(int index) {
        if (testData == null || !testData.has(CUSTOMERS_NODE)) {
            throw new RuntimeException("customers node not found in testdata.json");
        }

        JsonNode customersArray = testData.get(CUSTOMERS_NODE);
        if (index < 0 || index >= customersArray.size()) {
            throw new RuntimeException("Customer index out of bounds: " + index);
        }

        return customersArray.get(index);
    }

    /**
     * Get a custom field value from customer data by key.
     *
     * @param key the field key
     * @return the field value
     */
    public String getCustomerField(String key) {
        return getCustomerField(customerIndex, key);
    }

    /**
     * Get a custom field value from customer data by index and key.
     *
     * @param index the customer index in the JSON array
     * @param key   the field key
     * @return the field value
     */
    public String getCustomerField(int index, String key) {
        Objects.requireNonNull(key, "Field key cannot be null");
        JsonNode customer = getCustomer(index);
        if (customer.has(key) && !customer.get(key).isNull()) {
            return customer.get(key).asText();
        }
        return null;
    }
}
