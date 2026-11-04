package com.example.tests;

import org.testng.annotations.Test;

import com.example.base.BaseTest;
import com.example.pages.EngineerDiariesHomePage;
import com.example.pages.SignupPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class SignupTest extends BaseTest {
    @Test(groups = "sanity")
    public void validTestDataCanBeEnteredWithoutCreatingAnAccount() {
        new EngineerDiariesHomePage(page, config.getBaseUrl()).open().openSignup();
        SignupPage signupPage = new SignupPage(page);
        signupPage.fillCollegeStudentDetails(
                "Playwright Test User",
                "0000000000",
                "signup-test@example.invalid",
                "ValidTestPass123!",
                "India",
                "Delhi",
                "Bachelor of Technology",
                "Example Test College",
                "1st");

        assertEquals(signupPage.getFieldValue("fullName"), "Playwright Test User");
        assertEquals(signupPage.getFieldValue("phoneNumber"), "0000000000");
        assertEquals(signupPage.getFieldValue("email"), "signup-test@example.invalid");
        assertEquals(signupPage.getFieldValue("educationQualification"), "Bachelor of Technology");
        assertEquals(signupPage.getFieldValue("collegeName"), "Example Test College");
        assertEquals(signupPage.getSelectedValue("country"), "India");
        assertEquals(signupPage.getSelectedValue("state"), "Delhi");
        assertEquals(signupPage.getSelectedValue("profession"), "College Student");
        assertEquals(signupPage.getSelectedValue("collegeYear"), "1st");
        assertTrue(signupPage.isEmailFormatValid(), "A well-formed email should pass browser validation.");
    }

    @Test(groups = "sanity")
    public void malformedEmailFailsBrowserValidation() {
        new EngineerDiariesHomePage(page, config.getBaseUrl()).open().openSignup();
        SignupPage signupPage = new SignupPage(page);
        signupPage.enterEmail("not-an-email");

        assertFalse(signupPage.isEmailFormatValid(),
                "A malformed email should fail browser validation before form submission.");
        assertEquals(page.url(), config.getBaseUrl(),
                "The form must remain unsubmitted during this validation test.");
    }
}
