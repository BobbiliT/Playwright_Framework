package com.example.tests;

import org.testng.annotations.Test;

import com.example.base.BaseTest;
import com.example.pages.SeleniumPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class SeleniumPageTest extends BaseTest {
    @Test(groups = "smoke")
    public void seleniumPracticePageLoads() {
        SeleniumPage seleniumPage = new SeleniumPage(page, config.getBaseUrl()).open();

        assertFalse(seleniumPage.getTitle().isBlank(), "Page title should not be empty.");
        assertTrue(seleniumPage.isHeadingVisible("Web Elements for Automation Testing"));
        assertTrue(seleniumPage.isHeadingVisible("Employee Performance Details"));
        assertEquals(seleniumPage.getEmployeeRowCount(), 10,
                "The employee table should show its ten sample records.");
    }

    @Test(groups = "sanity")
    public void countryAndProgrammingLanguageControlsWork() {
        SeleniumPage seleniumPage = new SeleniumPage(page, config.getBaseUrl()).open();
        seleniumPage.selectCountry("India");
        seleniumPage.selectProgrammingLanguage("java");

        assertEquals(seleniumPage.getSelectedCountryValue(), "india");
        assertTrue(seleniumPage.isProgrammingLanguageSelected("java"),
                "Java should be selected after checking its option.");
    }

    @Test(groups = "regression")
    public void tabsAndIframeInputWorkAndDisabledControlStaysDisabled() {
        SeleniumPage seleniumPage = new SeleniumPage(page, config.getBaseUrl()).open();
        seleniumPage.selectTab("Tab 2");
        seleniumPage.enterFrameUsername("Playwright QA");

        assertTrue(seleniumPage.isTabSelected("Tab 2"));
        assertTrue(seleniumPage.isTabContentVisible("Tab 2"));
        assertEquals(seleniumPage.getFrameUsername(), "Playwright QA");
        assertTrue(seleniumPage.isDisabledButtonDisabled(),
                "The example disabled button should remain disabled.");
    }
}
