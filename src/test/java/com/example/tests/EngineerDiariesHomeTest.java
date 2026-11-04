package com.example.tests;

import org.testng.annotations.Test;

import com.example.base.BaseTest;
import com.example.pages.EngineerDiariesHomePage;

import static org.testng.Assert.assertFalse;

public class EngineerDiariesHomeTest extends BaseTest {
    @Test(groups = "smoke")
    public void homepageLoadsWithTitle() {
        EngineerDiariesHomePage homePage = new EngineerDiariesHomePage(page, config.getBaseUrl());
        homePage.open();

        assertFalse(homePage.getTitle().isBlank(), "Homepage should have a non-empty title.");
    }
}
