# Git Workflow Guide

## Branch Structure

We use a main/dev/feature branch structure for clean code management.

### Main Branches

- **main**: Production-ready code. Merges only from dev (via release)
- **dev**: Development branch. Integration point for features

### Feature Branches

- **feature/\***: New features
- **bugfix/\***: Bug fixes
- **refactor/\***: Code refactoring
- **test/\***: Test additions
- **docs/\***: Documentation updates

## Workflow Steps

### 1. Starting a Feature

```bash
# Make sure dev is up to date
git checkout dev
git pull origin dev

# Create feature branch
git checkout -b feature/my-new-feature

# or
git checkout -b feature/JIRA-123-description
```

### 2. During Development

```bash
# Make small, logical commits
git add .
git commit -m "feat: add login page automation"

# Another feature
git add .
git commit -m "feat: add login validation assertions"

# Push to remote (optional, but recommended)
git push origin feature/my-new-feature
```

### 3. Merging to Dev

```bash
# Switch to dev
git checkout dev

# Update dev with latest
git pull origin dev

# Merge feature
git merge feature/my-new-feature

# Push merged changes
git push origin dev
```

### 4. After Merge

```bash
# Delete local feature branch
git branch -d feature/my-new-feature

# Delete remote feature branch
git push origin --delete feature/my-new-feature

# List all branches
git branch -a
```

## Commit Message Format

Use clear, descriptive commit messages:

```
<type>: <subject>

<body>

<footer>
```

### Types:
- **feat**: A new feature
- **fix**: A bug fix
- **refactor**: Code change without feature/fix
- **test**: Test additions/changes
- **docs**: Documentation changes
- **chore**: Maintenance tasks

### Examples:

```bash
git commit -m "feat: add explicit wait strategy for element visibility"
git commit -m "fix: handle null driver in DriverManager"
git commit -m "refactor: extract driver creation logic into ChromeDriverFactory"
git commit -m "test: add LoginPage test cases"
git commit -m "docs: update README with SOLID principles explanation"
```

## Common Commands

### Check Status
```bash
git status
git log --oneline -10
git branch -v
```

### See Changes
```bash
git diff
git diff --cached
git show <commit-hash>
```

### Manage Branches
```bash
git branch -a              # List all branches
git branch -d <name>       # Delete local branch
git push origin --delete <name>  # Delete remote branch
git rename <old> <new>     # Rename branch
```

### Undo Changes
```bash
git restore <file>         # Discard changes in file
git restore --staged <file>  # Unstage file
git reset HEAD~1           # Undo last commit
git revert <commit-hash>   # Revert specific commit
```

## Best Practices

1. ✅ Create feature branches from dev
2. ✅ Keep commits small and logical
3. ✅ Write clear commit messages
4. ✅ Test before merging
5. ✅ Merge to dev, not directly to main
6. ✅ Pull before starting work
7. ✅ Review your own code before committing
8. ✅ Delete branches after merging

## Avoiding Conflicts

1. Pull latest changes before starting work
2. Keep branches short-lived (complete within days)
3. Commit frequently (multiple times per day)
4. Review changes before merging
5. Communicate with team about working areas

## If Conflict Occurs

```bash
# See conflicts
git status

# Edit conflicted files manually
# Look for <<<<<<, ======, >>>>>> markers

# After fixing
git add <resolved-file>
git commit -m "resolve: merge conflicts in <file>"
```

## Useful Aliases

Add these to your git config:

```bash
git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status
git config --global alias.lg "log --graph --oneline --all"
```

Then use shorter commands:
```bash
git co dev
git br -a
git lg
```
