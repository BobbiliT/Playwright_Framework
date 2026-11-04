package com.example.tests;

import org.testng.annotations.Test;

import com.example.base.ApiTest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ApiFrameworkTest extends ApiTest {
    private static final String PRODUCTS_ENDPOINT = "/api/testFramework/product";

    @Test(groups = "smoke")
    public void productListReturnsProducts() {
        loginAsAdmin();
        APIResponse response = api.get(PRODUCTS_ENDPOINT);
        assertEquals(response.status(), 200, "Product list should return HTTP 200.");

        JsonArray products = JsonParser.parseString(response.text()).getAsJsonArray();
        assertFalse(products.isEmpty(), "Product list should not be empty.");

        for (int i = 0; i < products.size(); i++) {
            JsonObject product = products.get(i).getAsJsonObject();
            assertTrue(product.has("id"), "Each product should have an id.");
            assertTrue(product.has("title"), "Each product should have a title.");
            assertTrue(product.has("price"), "Each product should have a price.");
        }
        response.dispose();
    }

    @Test(groups = "sanity")
    public void productDetailsReturnTheRequestedProduct() {
        loginAsAdmin();
        APIResponse response = api.get(PRODUCTS_ENDPOINT + "/1");
        assertEquals(response.status(), 200, "Product details should return HTTP 200.");

        JsonArray products = JsonParser.parseString(response.text()).getAsJsonArray();
        assertEquals(products.size(), 1, "The endpoint should return one matching product.");

        JsonObject product = products.get(0).getAsJsonObject();
        assertEquals(product.get("id").getAsInt(), 1);
        assertEquals(product.get("title").getAsString(), "iPhone 14");
        assertEquals(product.get("price").getAsDouble(), 80000.0);
        response.dispose();
    }

    @Test(groups = "regression")
    public void unknownProductIdReturnsNotFound() {
        loginAsAdmin();
        APIResponse response = api.get(PRODUCTS_ENDPOINT + "/999");
        assertEquals(response.status(), 404, "Unknown product should return HTTP 404.");

        JsonObject error = JsonParser.parseString(response.text()).getAsJsonObject();
        assertEquals(error.get("error").getAsString(), "No Data found");
        response.dispose();
    }

    private void loginAsAdmin() {
        APIResponse response = api.post("/api/testFramework/login",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData("{\"email\":\"admin\",\"password\":\"admin123\"}"));
        assertEquals(response.status(), 200, "Demo admin login should succeed.");
        response.dispose();
    }
}
