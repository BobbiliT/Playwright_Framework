package com.example.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class EngineerDiariesHomePage {
    private final Page page;
    private final String baseUrl;

    public EngineerDiariesHomePage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
    }

    public EngineerDiariesHomePage open() {
        page.navigate(baseUrl);
        return this;
    }

    public String getTitle() {
        return page.title();
    }

    public void openSignup() {
        page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Sign In / Sign Up")).click();
    }
}
