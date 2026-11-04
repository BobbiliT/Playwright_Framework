package com.example.pages;

import java.util.Locale;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SeleniumPage {
    private final Page page;
    private final String baseUrl;

    public SeleniumPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    public SeleniumPage open() {
        page.navigate(baseUrl + "selenium");
        page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Web Elements for Automation Testing").setExact(true)
                        .setLevel(2))
                .waitFor();
        return this;
    }

    public String getTitle() {
        return page.title();
    }

    public boolean isHeadingVisible(String heading) {
        return page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName(heading).setExact(true)).isVisible();
    }

    public int getEmployeeRowCount() {
        return page.getByRole(AriaRole.TABLE, new Page.GetByRoleOptions().setName("User Table"))
                .getByRole(AriaRole.ROW).count() - 1;
    }

    public void selectCountry(String country) {
        page.getByRole(AriaRole.COMBOBOX,
                new Page.GetByRoleOptions().setName("Select an Option:"))
                .selectOption(country.toLowerCase(Locale.ROOT));
    }

    public String getSelectedCountryValue() {
        return page.getByRole(AriaRole.COMBOBOX,
                new Page.GetByRoleOptions().setName("Select an Option:")).inputValue();
    }

    public void selectProgrammingLanguage(String languageValue) {
        page.locator("input[name='programmingLang'][value='" + languageValue + "']").check();
    }

    public boolean isProgrammingLanguageSelected(String languageValue) {
        return page.locator("input[name='programmingLang'][value='" + languageValue + "']").isChecked();
    }

    public boolean isDisabledButtonDisabled() {
        return page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Disabled").setExact(true)).isDisabled();
    }

    public void selectTab(String tabName) {
        tab(tabName).click();
    }

    public boolean isTabSelected(String tabName) {
        return "true".equals(tab(tabName).getAttribute("aria-pressed"));
    }

    public boolean isTabContentVisible(String tabName) {
        return page.locator("[data-testid='tab-element']")
                .getByText("Content of " + tabName, new Locator.GetByTextOptions().setExact(true))
                .isVisible();
    }

    public void enterFrameUsername(String username) {
        FrameLocator frame = page.frameLocator("iframe[title='Test Frame']");
        frame.getByRole(AriaRole.TEXTBOX,
                new FrameLocator.GetByRoleOptions().setName("Username")).fill(username);
    }

    public String getFrameUsername() {
        FrameLocator frame = page.frameLocator("iframe[title='Test Frame']");
        return frame.getByRole(AriaRole.TEXTBOX,
                new FrameLocator.GetByRoleOptions().setName("Username")).inputValue();
    }

    private Locator tab(String tabName) {
        return page.locator("[data-testid='tab-element']")
                .getByRole(AriaRole.BUTTON,
                        new Locator.GetByRoleOptions().setName(tabName).setExact(true));
    }
}
