# Selenium Lab 2 - Bank Application Test Automation

Comprehensive test automation suite for the XYZ Bank application using Selenium WebDriver, JUnit 5, and advanced testing patterns.

## Project Overview

This project demonstrates enterprise-level test automation including:
- **Page Object Model (POM)** for maintainable UI interactions
- **Comprehensive Logging** with SLF4J and Logback
- **Allure Reporting** for detailed test documentation
- **Input Validation Testing** to identify security vulnerabilities
- **BDD-style Step Annotations** for clear test execution flow

## Test Statistics

| Category | Count | Status |
|----------|-------|--------|
| Functional Tests | 6 | ✓ PASSING |
| Validation Tests | 4 | ✗ FAILING (Bug Documentation) |
| Total Tests | 10 | 60% Pass Rate |

## Test Coverage

### Customer Tests (3)
- `testDeposit()` - Deposit functionality with balance verification
- `testWithdraw()` - Withdrawal functionality with balance verification
- `testViewTransactions()` - Transaction history retrieval and validation

### Manager Tests (7)

#### Functional Tests (3)
- `testAddCustomer()` - Add new customer functionality
- `testCreateAccount()` - Create account for existing customer
- `testDeleteCustomer()` - Delete customer from system

#### Validation Tests (4) - Bug Documentation
- `testAddCustomerWithSpecialCharactersInPostalCode()` - Postal code accepts "12@#45"
- `testAddCustomerWithAlphabeticCharactersInPostalCode()` - Postal code accepts "ABC12"
- `testAddCustomerWithSpecialCharactersInFirstName()` - First name accepts "Jo@n"
- `testAddCustomerWithNumericCharactersInLastName()` - Last name accepts "Doe123"

## Identified Bugs

| Bug # | Field | Invalid Input | Expected | Actual | Severity |
|-------|-------|----------------|----------|--------|----------|
| 1 | Postal Code | Special chars (12@#45) | Numeric only | Accepted | Medium |
| 2 | Postal Code | Alphabetic (ABC12) | Numeric only | Accepted | Medium |
| 3 | First Name | Special char (Jo@n) | Alphanumeric | Accepted | High |
| 4 | Last Name | Numeric (Doe123) | Letters only | Accepted | High |

## Technology Stack

- **Language**: Java 21
- **Test Framework**: JUnit 5 (Jupiter)
- **Browser Automation**: Selenium WebDriver 4.15.0
- **Build Tool**: Maven 3.9.11
- **Logging**: SLF4J 2.0.9 + Logback 1.4.14
- **Reporting**: Allure 2.25.0
- **Browser**: Chrome (managed via WebDriverManager)
- **Application Under Test**: XYZ Bank (AngularJS)
  - URL: https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login

## Project Structure

```
src/
├── main/
│   ├── java/com/selenium/
│   │   ├── pages/          # Page Object Models
│   │   │   ├── LoginPage.java
│   │   │   ├── CustomerPage.java
│   │   │   └── ManagerPage.java
│   │   └── utils/          # Utility classes
│   │       └── ConfigReader.java
│   └── resources/
│       ├── config.properties
│       └── testdata.json
├── test/
│   ├── java/com/selenium/
│   │   ├── base/           # Test base classes
│   │   │   └── BaseTest.java
│   │   └── tests/          # Test classes
│   │       ├── CustomerTests.java
│   │       └── ManagerTests.java
│   └── resources/
│       └── logback-test.xml
target/
├── logs/                   # Test execution logs
│   ├── test-execution.log
│   └── test-results.log
└── surefire-reports/       # Maven test reports
pom.xml                    # Maven configuration
```

## Key Features

### 1. Page Object Model (POM)
- Encapsulates UI interactions in separate page classes
- Reduces code duplication and improves maintainability
- Easy element locator management

### 2. Comprehensive Logging
```
Step 1: Initializing LoginPage
Step 2: Clicking 'Customer Login' button
✓ Customer login page loaded
Step 3: Logging in as customer: Harry Potter
✓ Customer 'Harry Potter' logged in successfully
```

### 3. Input Validation Testing
- Negative test cases to identify security gaps
- Documents application bugs
- Provides regression test suite

### 4. Allure Reporting
- Detailed test documentation with @Epic, @Feature, @Story
- Step-by-step test execution tracking
- Attachment support for evidence

## Running Tests

### Run all tests
```bash
mvn test
```

### Run specific test class
```bash
mvn test -Dtest=CustomerTests
```

### Run specific test method
```bash
mvn test -Dtest=ManagerTests#testAddCustomer
```

### Run with quiet output
```bash
mvn test -q
```

### Generate Allure report
```bash
mvn allure:report
```

## Logging Output

Test execution logs are captured in two locations:

1. **Console Output** - Real-time colored output during test execution
2. **File Output**:
   - `target/logs/test-execution.log` - Complete execution details
   - `target/logs/test-results.log` - Test results summary (INFO level)

### Sample Log Entry
```
[TEST] Test Add Customer Functionality
Step 1: Click on 'Add Customer' tab
Step 2: Enter first name: John
Step 3: Enter last name: Doe
Step 4: Enter postal code: 12345
Step 5: Click 'Add Customer' button
✓ PASSED
```

## Test User Credentials

The application provides pre-configured test data:

### Customer
- Username: Harry Potter

### Manager
- Credentials: Use "Bank Manager Login" button

## Configuration

### Base URL
- Default: https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login
- Configured in: `src/main/resources/config.properties`

### Timeouts
- Implicit Wait: 10 seconds
- Page Load Timeout: 30 seconds
- Explicit Wait (Angular): 500ms - 1500ms per operation

## CI/CD Integration

This test suite is ready for CI/CD pipelines:
- Maven-based execution for easy integration
- Configurable test selection via Maven profiles
- Allure artifacts for reporting
- Exit codes indicate test status (0 = pass, 1 = fail)

## Git Workflow

### Branch Strategy
- `main` - Production-ready code
- `develop` - Integration branch for features
- `feature/*` - Feature development branches
- `hotfix/*` - Critical bug fixes

### Feature Branches
- `feature/logging-implementation` - SLF4J/Logback setup
- `feature/customer-tests` - Customer functional tests
- `feature/manager-functional-tests` - Manager functional tests
- `feature/postal-code-validation` - Postal code validation tests
- `feature/first-name-validation` - First name validation tests
- `feature/last-name-validation` - Last name validation tests

## Known Issues & Limitations

1. Application accepts special characters in postal code (Bug #1)
2. Application accepts alphabetic characters in postal code (Bug #2)
3. Application accepts special characters in first name (Bug #3)
4. Application accepts numeric characters in last name (Bug #4)

## Future Enhancements

- [ ] Add more negative test cases for other fields
- [ ] Implement data-driven testing with Excel/CSV
- [ ] Add API testing layer
- [ ] Security testing (SQL injection, XSS)
- [ ] Performance testing with load profiles
- [ ] Mobile application testing support

## Author

Test Automation Team

## License

Proprietary - XYZ Bank

## Contact

For issues or questions regarding this test suite, please contact the QA Team.
