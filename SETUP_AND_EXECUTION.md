# XYZ Bank Automation Framework - Setup and Execution Guide

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.8.0 or higher
- Git
- Google Chrome browser (for local testing)

### Local Setup and Execution

#### 1. Clone the Repository
```bash
git clone https://github.com/REGENCY-14/Seleniumlab2.git
cd Seleniumlab2
```

#### 2. Install Dependencies
```bash
mvn clean install
```

#### 3. Run All Tests
```bash
mvn clean test
```

#### 4. Run Specific Test Class
```bash
mvn test -Dtest=ManagerTests
mvn test -Dtest=CustomerTests
```

#### 5. Run Specific Test Method
```bash
mvn test -Dtest=ManagerTests#testAddCustomerValid
```

#### 6. Generate Allure Report
```bash
mvn allure:report
```

#### 7. View Allure Report
```bash
mvn allure:serve
```

## Docker Setup and Execution

### Build Docker Image
```bash
docker build -t xyz-bank-automation:latest .
```

### Run Tests in Docker
```bash
docker run --rm xyz-bank-automation:latest
```

### Run Tests with Volume Mounting (to save reports)
```bash
docker run --rm -v $(pwd)/target:/app/target xyz-bank-automation:latest
```

## Project Structure

```
Selenium2/
├── src/
│   ├── main/java/com/xyzbank/
│   │   ├── driver/
│   │   │   └── DriverFactory.java          # WebDriver factory with headless support
│   │   └── pages/
│   │       ├── ManagerPage.java            # Manager operations Page Object
│   │       └── CustomerPage.java           # Customer operations Page Object
│   └── test/java/com/xyzbank/
│       ├── base/
│       │   └── BaseTest.java               # JUnit 5 base test class
│       ├── tests/
│       │   ├── ManagerTests.java           # Manager test cases
│       │   └── CustomerTests.java          # Customer test cases
│       └── resources/
│           └── log4j2.xml                  # Logging configuration
├── pom.xml                                 # Maven dependencies and plugins
├── Dockerfile                              # Docker image configuration
├── .github/workflows/ci.yml               # GitHub Actions CI/CD pipeline
└── README.md                              # Project documentation
```

## Test Suite Overview

### Manager Tests (ManagerTests.java)
- **testAddCustomerValid**: Add customer with valid alphabetic names and numeric postal code
- **testAddCustomerInvalidFirstName**: Verify exception for non-alphabetic first name
- **testAddCustomerInvalidPostalCode**: Verify exception for non-numeric postal code
- **testAddMultipleCustomers**: Parameterized test for multiple customer additions
- **testCreateAccount**: Create account for existing customer
- **testDeleteAccount**: Delete customer account

### Customer Tests (CustomerTests.java)
- **testCustomerLogin**: Successful customer login
- **testDeposit**: Deposit positive amount
- **testDepositInvalidAmount**: Verify exception for negative/zero deposit
- **testWithdraw**: Withdraw money with sufficient balance
- **testWithdrawInsufficientBalance**: Verify insufficient balance error
- **testViewTransactions**: View transaction history
- **testBalanceUpdate**: Verify balance updates after transactions
- **testDepositVariousAmounts**: Parameterized test with various amounts
- **testTransactionHistoryReadOnly**: Verify transaction history is read-only
- **testLogout**: Customer logout

## Key Features

### 1. SOLID Principles
- **Single Responsibility**: Each class has one purpose
- **Open/Closed**: Extensible without modification (DriverFactory, Page Objects)
- **Liskov Substitution**: Interchangeable implementations
- **Interface Segregation**: Focused interfaces
- **Dependency Inversion**: Depends on abstractions, not concrete classes

### 2. Page Object Model (POM)
- Centralized element locators
- Reusable page methods
- Separated test logic from page interactions
- Easy maintenance and scalability

### 3. JUnit 5 Features
- Modern annotations (@BeforeEach, @AfterEach, @Test)
- Parameterized tests (@ParameterizedTest)
- Display names for better readability
- Extension mechanism for custom functionality

### 4. Allure Reporting
- Detailed test reports with screenshots
- Test execution history
- Trend analysis
- Integration with CI/CD pipelines

### 5. Logging
- Log4j2 for comprehensive logging
- Multiple log files (all logs, errors, rolling)
- Different log levels for different components
- Structured logging with timestamps

### 6. CI/CD Integration
- GitHub Actions workflow
- Automated test execution on push/PR
- Allure report generation and upload
- Docker support for containerized testing

## Configuration and Customization

### Headless Mode
Tests run in headless mode by default in Docker and CI/CD environments.
For local testing, modify DriverFactory or use system property:
```bash
mvn test -DargLine="-Dheadless=true"
```

### Implicit and Explicit Waits
- Explicit waits: 10 seconds (configurable in DriverFactory)
- No implicit waits (use explicit waits instead)

### Logging Levels
Configure in src/test/resources/log4j2.xml:
- DEBUG: Detailed diagnostic information
- INFO: General informational messages
- WARN: Warning messages
- ERROR: Error messages only

## Best Practices Implemented

1. **DRY Principle**: Reusable methods in BaseTest and Page Objects
2. **Meaningful Names**: Clear test and method names describing intent
3. **Test Independence**: Each test can run in isolation
4. **Proper Assertions**: Using JUnit 5 assertions for clarity
5. **Exception Handling**: Proper validation and error messages
6. **Resource Management**: WebDriver properly initialized and cleaned up
7. **Logging**: Comprehensive logging for debugging and monitoring
8. **Parameterized Tests**: Testing multiple scenarios efficiently

## Continuous Integration

### GitHub Actions Workflow
Triggered on:
- Push to main, dev, or feature branches
- Pull requests to main or dev
- Daily schedule (2 AM UTC)

### Workflow Steps
1. Checkout code
2. Setup Java 17
3. Install Chrome
4. Run Maven clean test
5. Generate Allure reports
6. Upload artifacts
7. Publish test results
8. Comment on PRs

## Troubleshooting

### WebDriver Not Found
- Ensure Chrome is installed
- Run: `mvn clean install` to download WebDriverManager

### Tests Failing in Headless Mode
- Check Chrome version compatibility
- Verify Chrome installation in Docker
- Check Selenium WebDriver version

### Allure Report Not Generating
- Ensure aspectjweaver dependency is available
- Run: `mvn allure:report`
- Check allure-results directory exists

### Logging Not Appearing
- Verify log4j2.xml is in src/test/resources/
- Check logging configuration
- Verify application loggers are configured

## Contributing Guidelines

1. Create feature branch from dev
2. Follow SOLID principles
3. Write comprehensive tests
4. Add meaningful log statements
5. Update documentation
6. Create clean commit messages
7. Submit pull request with description

## License

This project is licensed under MIT License

## Contact

For questions or issues, please contact the development team or create a GitHub issue.
