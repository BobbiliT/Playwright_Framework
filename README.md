# Playwright Java TestNG Framework

A small browser automation framework using Java 17, Maven, Playwright, TestNG, and the Page Object Model.

## Project structure

```text
src/
  main/java/com/example/
    config/     Browser and URL settings
    pages/      Page objects and browser actions
  test/java/com/example/
    base/       Shared browser setup and cleanup
    tests/      TestNG test cases
```

## Setup and run

Install Java 17+, Maven, and the Playwright browser:

```powershell
mvn exec:java "-Dexec.mainClass=com.microsoft.playwright.CLI" "-Dexec.args=install chromium"
```

Run all tests headlessly in Chromium:

```powershell
mvn test
```

The default site is `https://www.engineerdiaries.com/`. Override settings as needed:

```powershell
mvn test "-Dbrowser=firefox" "-Dheadless=false" "-DbaseUrl=https://example.com/"
```

Supported browsers: `chromium`, `firefox`, and `webkit`. Install the selected browser before running tests.

## Test groups

Tests are grouped by purpose: `smoke` checks critical pages load, `sanity` checks key controls, and `regression` checks existing interactions.

```powershell
mvn test "-Dgroups=smoke"
mvn test "-Dgroups=sanity"
mvn test "-Dgroups=regression"
```

The signup tests use synthetic data and do not submit the form or create an account. TestNG reports are written to `target/surefire-reports/`.

## CI

GitHub Actions runs the complete suite on pushes and pull requests using Java 17 and Chromium. Download the `surefire-test-reports` artifact from the workflow run to view the report.
