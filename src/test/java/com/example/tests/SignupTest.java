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
                "Bobbili Tejeswara Rao",
                "7036060404",
                "tejesh123@gmail.com",
                "Tejesh@123",
                "India",
                "Delhi",
                "Bachelor of Technology",
                "Example Test College",
                "1st");

        assertEquals(signupPage.getFieldValue("fullName"), "Bobbili Tejeswara Rao");
        assertEquals(signupPage.getFieldValue("phoneNumber"), "7036060404");
        assertEquals(signupPage.getFieldValue("email"), "tejesh123@gmail.com");
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
