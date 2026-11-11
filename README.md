# Homework 4 — Java + Playwright + JUnit 5 + Page Object

Five UI tests for [The Internet](https://the-internet.herokuapp.com/) written to
demonstrate the **Page Object Model**, correct Playwright API usage, and a
clean test lifecycle.

## Tech stack

- Java **25** (via Gradle toolchain)
- Gradle
- **Playwright for Java `1.64.0`**
- **JUnit 5** (JUnit Platform BOM)
- Page Object Model (POM)

## Test scenarios

| #   | Test method                                                | Scenario                                                            |
| --- | ---------------------------------------------------------- | ------------------------------------------------------------------- |
| 1   | `LoginTests.shouldLoginWithValidCredentials`               | Successful login → Secure Area is shown, success flash is displayed |
| 2   | `LoginTests.shouldShowErrorForInvalidCredentials`          | Invalid credentials → error flash is displayed                      |
| 3   | `LoginTests.shouldShowErrorForEmptyCredentials`            | Empty credentials → error flash is displayed                        |
| 4   | `CheckboxesTests.shouldCheckFirstAndUncheckSecondCheckbox` | Toggle two checkboxes (unchecked → checked, checked → unchecked)    |
| 5   | `DropdownTests.shouldSelectOptionTwoFromDropdown`          | Select `Option 2` and verify both `value` and visible text          |

Demo credentials are published on the login page:

- Username: `tomsmith`
- Password: `SuperSecretPassword!`

## Project structure

```text
dz4-java-playwright/
├── build.gradle
├── settings.gradle
├── flake.nix
├── README.md
└── src/test/java/com/dzollo/hw4/
    ├── BaseTest.java
    ├── pages/
    │   ├── BasePage.java
    │   ├── LoginPage.java
    │   ├── SecureAreaPage.java
    │   ├── CheckboxesPage.java
    │   └── DropdownPage.java
    └── tests/
        ├── LoginTests.java
        ├── CheckboxesTests.java
        └── DropdownTests.java
```
