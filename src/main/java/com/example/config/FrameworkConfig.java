package com.example.config;

import java.util.Locale;

public class FrameworkConfig {
    private static final String DEFAULT_BASE_URL = "https://www.engineerdiaries.com/";

    public String getBaseUrl() {
        return System.getProperty("baseUrl", DEFAULT_BASE_URL);
    }

    public String getBrowserName() {
        String browser = System.getProperty("browser", "chromium").toLowerCase(Locale.ROOT);
        if (!browser.equals("chromium") && !browser.equals("firefox") && !browser.equals("webkit")) {
            throw new IllegalArgumentException("Unsupported browser: " + browser
                    + ". Supported values are chromium, firefox, and webkit.");
        }
        return browser;
    }

    public boolean isHeadless() {
        String headless = System.getProperty("headless", "true");
        if (!headless.equalsIgnoreCase("true") && !headless.equalsIgnoreCase("false")) {
            throw new IllegalArgumentException("The headless property must be true or false.");
        }
        return Boolean.parseBoolean(headless);
    }
}
