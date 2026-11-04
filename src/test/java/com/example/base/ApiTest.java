package com.example.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.example.config.FrameworkConfig;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;

public abstract class ApiTest {
    protected final FrameworkConfig config = new FrameworkConfig();
    protected Playwright playwright;
    protected APIRequestContext api;

    @BeforeMethod(alwaysRun = true)
    public void setUpApi() {
        playwright = Playwright.create();
        api = playwright.request().newContext(
                new APIRequest.NewContextOptions().setBaseURL(config.getBaseUrl()));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownApi() {
        try {
            if (api != null) {
                api.dispose();
            }
        } finally {
            if (playwright != null) {
                playwright.close();
            }
        }
    }
}
