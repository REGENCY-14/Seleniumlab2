# 🚀 XYZ Bank Framework - Quick Reference Card

## Common Commands

### Building & Testing
```bash
# Clean and build
mvn clean install

# Run all tests
mvn clean test

# Run specific class
mvn test -Dtest=ManagerTests
mvn test -Dtest=CustomerTests

# Run specific method
mvn test -Dtest=ManagerTests#testAddCustomerValid

# Headless mode (CI/CD)
mvn test -DargLine="-Dheadless=true"

# Parallel execution (4 threads)
mvn test -DargLine="-n 4"
```

### Reporting
```bash
# Generate Allure report
mvn allure:report

# View Allure report (opens in browser)
mvn allure:serve

# Clean allure results
rm -rf allure-results
```

### Docker
```bash
# Build image
docker build -t xyz-bank-automation:latest .

# Run tests in Docker
docker run --rm xyz-bank-automation:latest

# Save reports to current directory
docker run --rm -v $(pwd)/target:/app/target xyz-bank-automation:latest

# Interactive shell
docker run -it --rm xyz-bank-automation:latest /bin/bash
```

## File Locations

```
Project Root: C:\Users\ZakariaOsman\Desktop\Selenium2\

Source Code:
├── src/main/java/com/xyzbank/
│   ├── driver/DriverFactory.java
│   └── pages/
│       ├── ManagerPage.java
│       └── CustomerPage.java
│
└── src/test/java/com/xyzbank/
    ├── base/BaseTest.java
    ├── tests/ManagerTests.java
    └── tests/CustomerTests.java

Configuration:
├── pom.xml
├── Dockerfile
├── src/test/resources/log4j2.xml
└── .github/workflows/ci.yml

Documentation:
├── README.md
├── SETUP_AND_EXECUTION.md
├── GIT_WORKFLOW.md
└── FRAMEWORK_COMPLETION_REPORT.md

Output:
├── logs/
│   ├── all-tests.log
│   ├── errors.log
│   └── rolling-tests.log
├── target/
│   ├── surefire-reports/
│   ├── allure-results/
│   └── test-classes/
```

## Page Objects

### ManagerPage - Add Customer
```java
ManagerPage managerPage = new ManagerPage(driver);

// Valid customer (alphabetic name, numeric postal code)
managerPage.addCustomer("John", "Doe", "12345");

// Invalid input throws IllegalArgumentException
managerPage.addCustomer("John123", "Doe", "12345"); // ❌ Non-alphabetic
managerPage.addCustomer("John", "Doe", "ABC123");   // ❌ Non-numeric

// Verify existence
boolean exists = managerPage.isCustomerExists("John Doe");
```

### ManagerPage - Account Operations
```java
ManagerPage managerPage = new ManagerPage(driver);

// Create account
managerPage.createAccount("John Doe", "Dollar");

// Delete account
managerPage.deleteAccount("John Doe");
```

### CustomerPage - Login & Balance
```java
CustomerPage customerPage = new CustomerPage(driver);

// Login
customerPage.login("Harry Potter");

// Get balance
double balance = customerPage.getBalance(); // Returns Double

// Logout
customerPage.logout();
```

### CustomerPage - Transactions
```java
CustomerPage customerPage = new CustomerPage(driver);

customerPage.login("Hermoine Granger");

// Deposit (positive only)
customerPage.deposit(500.0);        // ✅ Valid
customerPage.deposit(-100.0);       // ❌ Throws exception
customerPage.deposit(0);            // ❌ Throws exception

// Withdraw (positive & sufficient balance)
customerPage.withdraw(100.0);       // ✅ Valid if balance >= 100
customerPage.withdraw(999999.0);    // ❌ Insufficient balance error

// Check for error
if (customerPage.hasInsufficientBalanceError()) {
    // Handle insufficient balance
}

// View transactions
int transactionCount = customerPage.viewTransactions();
List<String> history = customerPage.getTransactionHistory();
```

## Writing New Tests

### Template
```java
package com.xyzbank.tests;

import com.xyzbank.tests.base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Feature("My Feature")
@DisplayName("My Test Suite")
public class MyTests extends BaseTest {
    
    private MyPage myPage;
    
    @BeforeEach
    public void setupTest() {
        super.setUp();
        navigateTo("https://example.com");
        myPage = new MyPage(driver);
    }
    
    @Test
    @DisplayName("Test Name")
    @Story("User Story")
    @Description("Detailed test description")
    @Severity(SeverityLevel.CRITICAL)
    public void testSomething() {
        // Arrange
        
        // Act
        myPage.someAction();
        
        // Assert
        assertTrue(condition);
    }
}
```

## Creating New Page Objects

### Template
```java
package com.xyzbank.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

public class MyPage {
    private static final Logger logger = LogManager.getLogger(MyPage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    
    @FindBy(xpath = "//button[@id='myButton']")
    private WebElement myButton;
    
    public MyPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        logger.info("MyPage initialized");
    }
    
    public void myAction() {
        logger.info("Performing my action");
        wait.until(ExpectedConditions.elementToBeClickable(myButton)).click();
    }
}
```

## Logging Usage

### In Page Objects & Tests
```java
logger.debug("Detailed debug information");
logger.info("General informational message");
logger.warn("Warning message - something odd");
logger.error("Error message - something failed");
```

### View Logs
```bash
# Watch live logs during test
tail -f logs/all-tests.log

# View errors only
cat logs/errors.log

# Search for specific test
grep "MyTest" logs/all-tests.log
```

## Allure Annotations

```java
@Feature("Feature Name")              // Feature grouping
@Story("Story Name")                  // Story grouping
@Description("Test description")      // Detailed description
@Severity(SeverityLevel.CRITICAL)    // CRITICAL, HIGH, MEDIUM, LOW
@DisplayName("Display Name")         // Friendly test name
```

## Git Commands Cheat Sheet

### Branch Management
```bash
# Create feature branch
git checkout -b feature/my-feature

# List all branches
git branch -a

# View branch details
git branch -v

# Delete local branch
git branch -d feature/my-feature

# Delete remote branch
git push origin --delete feature/my-feature
```

### Commits
```bash
# Stage all changes
git add .

# Commit with message
git commit -m "feat: add new feature"

# View commit log
git log --oneline -10

# View detailed changes
git show <commit-hash>
```

### Merging
```bash
# Merge feature to dev
git checkout dev
git pull origin dev
git merge feature/my-feature

# Resolve conflicts
# (edit files, then:)
git add .
git commit -m "resolve: merge conflicts"
```

## Troubleshooting

### WebDriver Not Starting
```bash
# Problem: ChromeDriver not found
# Solution: 
mvn clean install
# This downloads WebDriverManager which handles ChromeDriver

# Or manually update WebDriverManager version in pom.xml
```

### Tests Failing with Timeout
```bash
# Problem: Element not found within 10 seconds
# Solution: Check if element locator is correct

// In page object, verify @FindBy locator:
@FindBy(xpath = "//button[contains(text(), 'Click me')]")
// Check in browser developer tools that element exists
```

### Allure Report Not Generating
```bash
# Problem: No allure-results directory
# Solution:
mvn allure:report

# If still fails, clear and retry:
rm -rf allure-results
mvn clean test
mvn allure:report
```

### Docker Permission Denied
```bash
# Problem: docker: permission denied
# Solution (Linux/Mac):
sudo docker build -t xyz-bank-automation:latest .
# Or add user to docker group:
sudo usermod -aG docker $USER
newgrp docker
```

## Performance Tips

1. **Parallel Execution**
   ```bash
   mvn test -DargLine="-n 4"  # Run 4 tests in parallel
   ```

2. **Skip Compilation**
   ```bash
   mvn test -DskipTests=false
   ```

3. **Selective Testing**
   ```bash
   mvn test -Dtest=ManagerTests  # Run only one class
   ```

4. **Skip Logging (faster)**
   Edit log4j2.xml and set root level to WARN

## Team Collaboration

### Making a Change
```bash
# 1. Start with latest dev
git checkout dev
git pull origin dev

# 2. Create feature branch
git checkout -b feature/my-feature

# 3. Make changes, test locally
mvn clean test

# 4. Commit with message
git add .
git commit -m "feat: describe your feature"

# 5. Push to GitHub
git push origin feature/my-feature

# 6. Create Pull Request on GitHub

# 7. After merge, delete feature branch
git branch -d feature/my-feature
git push origin --delete feature/my-feature
```

### Code Review Checklist
- [ ] Tests pass locally
- [ ] Follows SOLID principles
- [ ] Proper logging added
- [ ] Comments for complex logic
- [ ] No hardcoded values
- [ ] Page Object pattern followed
- [ ] Meaningful commit messages

## Resources

- [README.md](README.md) - Full project documentation
- [SETUP_AND_EXECUTION.md](SETUP_AND_EXECUTION.md) - Detailed setup guide
- [GIT_WORKFLOW.md](GIT_WORKFLOW.md) - Git best practices
- [FRAMEWORK_COMPLETION_REPORT.md](FRAMEWORK_COMPLETION_REPORT.md) - Completion details

## Quick Links

- Project: `C:\Users\ZakariaOsman\Desktop\Selenium2`
- Main Documentation: `README.md`
- Setup Guide: `SETUP_AND_EXECUTION.md`
- CI/CD Workflow: `.github/workflows/ci.yml`
- Test Results: `target/surefire-reports/`
- Allure Reports: `allure-results/`
- Logs: `logs/`

---

**Framework Version**: 1.0.0  
**Last Updated**: February 2026  
**Status**: Production Ready ✅
