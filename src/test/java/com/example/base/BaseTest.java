package com.example.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.example.config.FrameworkConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public abstract class BaseTest {
    protected final FrameworkConfig config = new FrameworkConfig();
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeMethod(alwaysRun = true)
    public void setUpBrowser() {
        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(config.isHeadless());

        browser = switch (config.getBrowserName()) {
            case "chromium" -> playwright.chromium().launch(options);
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            default -> throw new IllegalStateException("Browser validation did not reject an unsupported browser.");
        };
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownBrowser() {
        try {
            if (context != null) {
                context.close();
            }
        } finally {
            try {
                if (browser != null) {
                    browser.close();
                }
            } finally {
                if (playwright != null) {
                    playwright.close();
                }
            }
        }
    }
}
