package com.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SignupPage {
    private final Page page;

    public SignupPage(Page page) {
        this.page = page;
    }

    public SignupPage fillCollegeStudentDetails(String fullName, String phone, String email,
            String password, String country, String state, String education, String collegeName,
            String collegeYear) {
        field("fullName").fill(fullName);
        field("phoneNumber").fill(phone);
        field("email").fill(email);
        field("password").fill(password);
        selectSearchableOption("country", country);
        selectSearchableOption("state", state);
        field("educationQualification").fill(education);
        selectProfession("College Student");
        field("collegeName").fill(collegeName);
        selectCollegeYear(collegeYear);
        return this;
    }

    public void enterEmail(String email) {
        field("email").fill(email);
    }

    public String getFieldValue(String name) {
        return field(name).inputValue();
    }

    public boolean isEmailFormatValid() {
        return Boolean.TRUE.equals(field("email").evaluate("element => element.checkValidity()"));
    }

    public String getSelectedValue(String selectName) {
        return form().locator("[name='" + selectName + "'] .ant-select-selection-item").innerText();
    }

    private void selectSearchableOption(String selectName, String value) {
        Locator select = form().locator("[name='" + selectName + "']");
        select.locator(".ant-select-selector").click();
        Locator search = select.locator("input[role='combobox']");
        search.fill(value);
        search.press("Enter");
    }

    private void selectProfession(String profession) {
        Locator select = form().locator("[name='profession']");
        select.locator(".ant-select-selector").click();
        page.keyboard().press("Home");
        page.keyboard().press("Enter");
        String selectedProfession = getSelectedValue("profession");
        if (!profession.equals(selectedProfession)) {
            throw new IllegalStateException("Expected profession '" + profession
                    + "' but selected '" + selectedProfession + "'.");
        }
    }

    private void selectCollegeYear(String collegeYear) {
        Locator select = form().locator("[name='collegeYear']");
        select.locator(".ant-select-selector").click();
        page.keyboard().press("1st".equals(collegeYear) ? "Home" : "End");
        page.keyboard().press("Enter");
    }

    private Locator field(String name) {
        return form().locator("input[name='" + name + "']");
    }

    private Locator form() {
        return page.locator("form").filter(new Locator.FilterOptions().setHasText("Create Account"));
    }
}
