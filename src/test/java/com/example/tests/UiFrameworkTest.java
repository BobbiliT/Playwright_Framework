package com.example.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.annotations.Test;

import com.example.base.BaseTest;
import com.example.pages.UiFrameworkPage;

public class UiFrameworkTest extends BaseTest {
    @Test(groups = "smoke")
    public void catalogPageLoadsWithProducts() {
        UiFrameworkPage catalog = new UiFrameworkPage(page, config.getBaseUrl()).open();

        assertFalse(catalog.getTitle().isBlank(), "The catalog page should have a title.");
        assertTrue(catalog.isHeadingVisible("GreenMarket"), "The catalog heading should be visible.");
        assertTrue(catalog.isHeadingVisible("Rose"), "A product should be visible on the catalog page.");
    }

    @Test(groups = "sanity")
    public void searchFindsProductsAndShowsEmptyStateForUnknownQuery() {
        UiFrameworkPage catalog = new UiFrameworkPage(page, config.getBaseUrl()).open();
        catalog.searchFor("Daisy");
        catalog.waitForFlowerHidden("Rose");

        assertTrue(catalog.isHeadingVisible("Daisy"), "Search should keep the matching product visible.");
        assertFalse(catalog.isHeadingVisible("Rose"), "Search should hide products that do not match.");

        catalog.searchFor("Tulip");
        catalog.waitForNoItemsMessage();
        assertTrue(catalog.isNoItemsMessageVisible());
    }

    @Test(groups = "regression")
    public void priceFilterAndSortReturnTheExpectedProductsInOrder() {
        UiFrameworkPage catalog = new UiFrameworkPage(page, config.getBaseUrl()).open();
        catalog.filterByPrice("low");

        assertEquals(catalog.getVisibleFlowers(),
                List.of("Rose", "Tulip", "Daisy", "Sunflower", "Chrysanthemum"),
                "Below-60 filter should show only products below ₹60.");

        catalog.sortHighToLow();
        assertEquals(catalog.getVisibleFlowers(),
                List.of("Rose", "Chrysanthemum", "Tulip", "Sunflower", "Daisy"),
                "High-to-low sorting should order the filtered products by price.");
    }

    @Test(groups = "regression")
    public void inStockFilterHidesUnavailableProducts() {
        UiFrameworkPage catalog = new UiFrameworkPage(page, config.getBaseUrl()).open();
        catalog.selectInStockOnly();

        assertEquals(catalog.getVisibleFlowers().size(), 6,
                "In-stock filter should hide the two unavailable products.");
        assertFalse(catalog.isHeadingVisible("Lily"), "Out-of-stock Lily should be hidden.");
        assertFalse(catalog.isHeadingVisible("Sunflower"), "Out-of-stock Sunflower should be hidden.");
        assertTrue(catalog.isHeadingVisible("Rose"), "An available product should remain visible.");
    }
}
