# Git Workflow Documentation

## Branch Strategy

This project uses a Git Flow workflow with the following branch structure:

### Main Branches

| Branch | Purpose | Protection |
|--------|---------|-----------|
| `main` | Production-ready code | ✓ Protected |
| `dev` | Integration branch for features | ✓ Protected |

### Feature Branches

Feature branches are created off `dev` and merged back after completion:

```
feature/postal-code-validation
feature/first-name-validation
feature/last-name-validation
feature/logging-implementation
feature/customer-tests
feature/manager-functional-tests
feature/manager-page-pom
feature/customer-page-pom
feature/login-page-pom
[... other feature branches ...]
```

## Branch Hierarchy

```
main (production)
  ↓ (merge)
dev (integration)
  ↗ (merge from)
feature/* (development)
```

## Current Branch Status

### Remote Branches on GitHub

```
remotes/origin/main                                    → Production branch
remotes/origin/dev                                     → Integration branch
remotes/origin/feature/postal-code-validation         → Postal code tests
remotes/origin/feature/first-name-validation          → First name tests
remotes/origin/feature/last-name-validation           → Last name tests
remotes/origin/HEAD -> origin/main                    → Default branch
```

### Local Branches

```
* main (current)
  dev
  appmod/java-upgrade-20260222193915
  feature/add-documentation
  feature/add-test-listeners
  feature/add-utilities
  feature/allure-reporting
  feature/config-reader-utility
  feature/customer-page-pom
  feature/customer-tests
  feature/github-actions-workflow
  feature/login-page-pom
  feature/manager-page-pom
  feature/manager-tests
  feature/postal-code-validation
  feature/first-name-validation
  feature/last-name-validation
  feature/setup-maven-junit5-selenium
  feature/setup-project-structure
  feature/test-data-reader
  feature/xyz-bank-app-framework
```

## Merge History

### Latest Commits on Main

```
4cbc509 (HEAD -> main) Merge dev with all validation tests and features to main
b3828be (dev, appmod/java-upgrade-20260222193915) Initial project setup with README and .gitignore
5dc440b Remove temporary test file
b3cdc4b fix issues
8884094 Upgrade Java from version 11 to 21 using OpenRewrite migration recipe
```

## Development Workflow

### 1. Starting a New Feature

```bash
# Update dev branch
git checkout dev
git pull origin dev

# Create feature branch
git checkout -b feature/feature-name

# Make changes and commit
git add .
git commit -m "feat(scope): description of changes"
```

### 2. Pushing Feature Branch

```bash
# Push to remote
git push -u origin feature/feature-name
```

### 3. Creating Pull Request

1. Go to GitHub repository
2. Create Pull Request from `feature/feature-name` → `dev`
3. Add description and link to related issues
4. Request review from team members
5. Merge after approval

### 4. Merging to Development

```bash
# Switch to dev
git checkout dev

# Merge feature
git merge feature/feature-name

# Push to remote
git push origin dev
```

### 5. Releasing to Production

```bash
# Switch to main
git checkout main

# Merge dev
git merge dev

# Tag release
git tag -a v1.0.0 -m "Release version 1.0.0"

# Push to remote
git push origin main
git push origin v1.0.0
```

## Commit Message Convention

We follow Conventional Commits for clear commit history:

### Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Types

- **feat**: A new feature
- **fix**: A bug fix
- **test**: Adding or updating tests
- **docs**: Documentation changes
- **style**: Code style changes (formatting, missing semicolons, etc.)
- **refactor**: Code refactoring without feature or bug changes
- **perf**: Performance improvements
- **chore**: Maintenance tasks and dependencies
- **ci**: CI/CD configuration changes

### Examples

```bash
git commit -m "feat(tests): add postal code validation tests"
git commit -m "fix(pages): update balance element locator"
git commit -m "test(validation): add first name validation test"
git commit -m "docs(readme): update test coverage section"
git commit -m "refactor(base): improve BaseTest setup method"
```

## Validation Test Branches

### 1. feature/postal-code-validation

**Tests:**
- `testAddCustomerWithSpecialCharactersInPostalCode()` - Tests "12@#45"
- `testAddCustomerWithAlphabeticCharactersInPostalCode()` - Tests "ABC12"

**Status:** Ready for merge to main
**Last Updated:** 2026-02-23

### 2. feature/first-name-validation

**Tests:**
- `testAddCustomerWithSpecialCharactersInFirstName()` - Tests "Jo@n"

**Status:** Ready for merge to main
**Last Updated:** 2026-02-23

### 3. feature/last-name-validation

**Tests:**
- `testAddCustomerWithNumericCharactersInLastName()` - Tests "Doe123"

**Status:** Ready for merge to main
**Last Updated:** 2026-02-23

## Merging Feature Branches to Dev and Main

All validation test features have been merged through the following workflow:

1. **Created** feature branches for each validation test
2. **Merged** appmod/java-upgrade-20260222193915 → dev (fast-forward)
3. **Merged** dev → main (3-way merge with conflict resolution)
4. **Pushed** all branches to GitHub

## Remote Configuration

```
URL: https://github.com/REGENCY-14/Seleniumlab2.git
Owner: REGENCY-14
Repository: Seleniumlab2
```

## Useful Commands

### View branch information

```bash
# List local branches
git branch

# List remote branches
git branch -r

# List all branches
git branch -a

# Show branch tracking
git branch -vv
```

### Synchronize branches

```bash
# Fetch updates
git fetch origin

# Pull changes
git pull origin main
git pull origin dev

# Push changes
git push origin main
git push origin dev
```

### Clean up branches

```bash
# Delete local branch
git branch -d feature/feature-name

# Delete remote branch
git push origin --delete feature/feature-name
```

### View history

```bash
# Show commit log
git log --oneline -10

# Show branch history
git log --graph --all --oneline

# Show what changed
git diff main dev
```

## Continuous Integration

### GitHub Actions

The repository includes GitHub Actions workflow for:
- Automated Maven builds
- JUnit 5 test execution
- Allure report generation
- Test result artifacts

Workflow file: `.github/workflows/maven-test.yml`

## Recommendations

1. **Always pull before pushing** to avoid conflicts
2. **Use descriptive commit messages** following convention
3. **Create small, focused feature branches** for easier review
4. **Keep feature branches short-lived** (< 1 week)
5. **Test locally before pushing** to reduce CI failures
6. **Rebase instead of merge** to keep history clean (when appropriate)

## Migration Notes

- Project was upgraded from Java 11 to Java 21 using OpenRewrite
- Branch `appmod/java-upgrade-20260222193915` contains the upgrade
- All tests pass with Java 21

## Support

For issues or questions about the git workflow, contact the QA Team or create an issue on GitHub.
