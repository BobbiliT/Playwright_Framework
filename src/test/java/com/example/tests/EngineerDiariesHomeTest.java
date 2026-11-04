package com.example.tests;

import static org.testng.Assert.assertFalse;
import org.testng.annotations.Test;

import com.example.base.BaseTest;
import com.example.pages.EngineerDiariesHomePage;

public class EngineerDiariesHomeTest extends BaseTest {
    @Test
    public void homepageLoadsWithTitle() {
        EngineerDiariesHomePage homePage = new EngineerDiariesHomePage(page, config.getBaseUrl());
        homePage.open();

        System.out.println("Page title: " + homePage.getTitle());

        assertFalse(homePage.getTitle().isBlank(), "Homepage should have a non-empty title.");
    }
}
