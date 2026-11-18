# Homework 6 — Test Design Patterns (Java + Playwright + JUnit 5)

UI tests for [The Internet](https://the-internet.herokuapp.com/), built on top of Homework 4.
The project is refactored to demonstrate **test automation design patterns**:
extensible, reusable and maintainable test code with parallel execution support.

## Tech stack

- Java **25** (via Gradle toolchain)
- Gradle
- **Playwright for Java `1.64.0`**
- **JUnit 5** (JUnit Platform BOM), parallel execution enabled

## Patterns used

### Base patterns

| Pattern                | Where                                                                            | How it is used                                                                             |
| ---------------------- | -------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------ |
| Page Object            | `pages/*` (`LoginPage`, `SecureAreaPage`, `CheckboxesPage`, `DropdownPage`)      | Each page hides locators and actions; tests contain no selectors                           |
| Page Element / Wrapper | `elements/*` (`Element`, `Input`, `Button`, `Checkbox`, `Select`, `FlashBanner`) | Wrappers over Playwright `Locator`; pages work with typed elements instead of raw locators |
| Loadable Component     | `core/LoadableComponent`, `pages/BasePage`                                       | `get()` opens a page only if needed (`load()`) and verifies its state (`isLoaded()`)       |
| Chain of invocations   | page methods returning `this` or the next page                                   | `loginPage.loginAs(creds).logout()`, `checkboxesPage.check(0).uncheck(1)`                  |
| Value Object           | `model/*` (`Credentials`, `FlashMessage`, `DropdownOption`)                      | Immutable, compared by value: `assertEquals(expectedMessage, page.flashMessage())`         |

### Additional patterns

| Pattern       | Where                     | How it is used                                                             |
| ------------- | ------------------------- | -------------------------------------------------------------------------- |
| Data Provider | `data/LoginDataProvider`  | Supplies invalid-login scenarios to a parameterized test (`@MethodSource`) |
| Factory       | `data/CredentialsFactory` | Builds credential variants: valid, unknown user, wrong password, empty     |
| Data Registry | `data/TestDataRegistry`   | Single place for the valid user and expected application messages          |

## Test scenarios

| Class             | Scenario                                                                            |
| ----------------- | ----------------------------------------------------------------------------------- |
| `LoginTests`      | Successful login → Secure Area opened, success flash shown                          |
| `LoginTests`      | Login → logout chain, logout flash shown                                            |
| `LoginTests`      | Parameterized: 4 invalid credential sets → expected error flash                     |
| `LoginTests`      | `@Disabled` demo: account lock after several failed attempts (not in the app)       |
| `CheckboxesTests` | Default state: first unchecked, second checked                                      |
| `CheckboxesTests` | Check the first and uncheck the second checkbox                                     |
| `CheckboxesTests` | `@TestFactory`: every checkbox toggles to the opposite state                        |
| `DropdownTests`   | Placeholder is selected by default                                                  |
| `DropdownTests`   | Parameterized (`@EnumSource`): select `Option 1` / `Option 2`, check value and text |

Demo credentials are published on the login page:

- Username: `tomsmith`
- Password: `SuperSecretPassword!`

## Parallel execution and isolation

- Parallel mode is configured in `src/test/resources/junit-platform.properties`
  (classes and methods run concurrently, fixed pool of 4 threads).
- Playwright objects are not thread-safe, so `BaseTest` creates its own `Playwright`,
  `Browser`, `BrowserContext` and `Page` for **every test** and closes them afterwards.
- The `@TestFactory` test runs in `SAME_THREAD` mode because its dynamic tests share one page.
- Trace file names contain class, method and a display-name hash, so parallel tests never overwrite each other.
- Requests to third-party hosts are blocked to keep tests fast and independent of external resources.

## Project structure

```text
dz6-java-playwright/
├── build.gradle
├── settings.gradle
├── flake.nix
├── README.md
└── src/test/
    ├── resources/
    │   └── junit-platform.properties
    └── java/com/dzollo/hw4/
        ├── BaseTest.java
        ├── core/
        │   └── LoadableComponent.java
        ├── elements/
        │   ├── Element.java
        │   ├── Input.java
        │   ├── Button.java
        │   ├── Checkbox.java
        │   ├── Select.java
        │   └── FlashBanner.java
        ├── model/
        │   ├── Credentials.java
        │   ├── FlashMessage.java
        │   └── DropdownOption.java
        ├── data/
        │   ├── TestDataRegistry.java
        │   ├── CredentialsFactory.java
        │   └── LoginDataProvider.java
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

## Running the tests

```bash
./gradlew test
```

Playwright traces of every test are saved to `build/playwright-artifacts/`
and can be opened at <https://trace.playwright.dev>.
