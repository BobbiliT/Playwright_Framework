# Playwright Java TestNG Framework

Java 17 browser automation framework using Maven, Playwright, TestNG, and the Page Object Model.

## Project layout

- `src/main/java/com/example/config/` - browser and run configuration
- `src/main/java/com/example/pages/` - page objects and UI actions
- `src/test/java/com/example/base/` - shared Playwright test lifecycle
- `src/test/java/com/example/tests/` - TestNG smoke, sanity, and regression tests

## Setup

Install the Playwright WebKit browser (the current default):

```powershell
mvn exec:java "-Dexec.mainClass=com.microsoft.playwright.CLI" "-Dexec.args=install webkit"
```

## Run tests

```powershell
mvn test
```

By default, tests run in a visible WebKit browser against `https://www.engineerdiaries.com/`. To run headlessly with Chromium:

```powershell
mvn test "-Dbrowser=chromium" "-Dheadless=true"
```

Override settings with Maven system properties (the matching Playwright browser must be installed):

```powershell
mvn test "-Dbrowser=chromium" "-Dheadless=true" "-DbaseUrl=https://example.com"
```

Supported browsers are `chromium`, `firefox`, and `webkit`; install the matching Playwright browser before selecting it.

The signup tests use synthetic data to check valid field entry and malformed-email browser validation. They do not click **Create Account**, so they do not create an account on the live site. Maven Surefire writes test results to `target/surefire-reports/`.

The UI Framework catalog tests cover page loading, product search and empty results, price filtering and sorting, and the in-stock filter. The Selenium practice-page tests cover smoke checks for page/table loading, sanity checks for country and language controls, and regression checks for tabs, iframe input, and disabled controls. Run all tests with `mvn test`, or select a test group:

```powershell
mvn test "-Dgroups=smoke"
mvn test "-Dgroups=sanity"
mvn test "-Dgroups=regression"
```

## Git and CI

This project includes a GitHub Actions workflow at `.github/workflows/ci.yml`. It runs the Maven TestNG suite on pushes, pull requests, or when manually started from the Actions tab. CI uses Java 17 and headless Chromium and uploads Surefire reports, even when tests fail.

Initialize Git and create the first commit from the project directory:

```powershell
git init
git add .
git commit -m "Initial Playwright Java TestNG framework"
```

To publish to GitHub, create an empty GitHub repository, then connect it and push:

```powershell
git remote add origin https://github.com/<YOUR-USER>/<YOUR-REPOSITORY>.git
git branch -M main
git push -u origin main
```

After pushing, GitHub Actions runs the CI workflow automatically. Pull requests also run the suite.
