package com.example.pages;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class UiFrameworkPage {
    private static final List<String> FLOWERS = List.of(
            "Rose", "Tulip", "Lily", "Orchid", "Daisy", "Sunflower", "Chrysanthemum", "Peony");

    private final Page page;
    private final String baseUrl;

    public UiFrameworkPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    public UiFrameworkPage open() {
        page.navigate(baseUrl + "ui-framework");
        page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("GreenMarket").setExact(true))
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return this;
    }

    public String getTitle() {
        return page.title();
    }

    public boolean isHeadingVisible(String heading) {
        return page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName(heading).setExact(true)).isVisible();
    }

    public void searchFor(String query) {
        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Search Flowers...")).fill(query);
    }

    public void waitForNoItemsMessage() {
        page.getByText("No Items found.", new Page.GetByTextOptions().setExact(true))
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    public boolean isNoItemsMessageVisible() {
        return page.getByText("No Items found.", new Page.GetByTextOptions().setExact(true)).isVisible();
    }

    public void waitForFlowerHidden(String flower) {
        flowerHeading(flower).waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    }

    public void filterByPrice(String value) {
        page.locator("select").nth(0).selectOption(value);
        waitForFlowerHidden("Orchid");
    }

    public void sortHighToLow() {
        page.locator("select").nth(1).selectOption("desc");
        page.waitForFunction(
                "expected => {"
                        + "const products = ['Rose', 'Tulip', 'Lily', 'Orchid', 'Daisy', 'Sunflower', 'Chrysanthemum', 'Peony'];"
                        + "const visible = Array.from(document.querySelectorAll('h3'))"
                        + ".filter(h => products.includes(h.innerText.trim()) && h.getClientRects().length > 0)"
                        + ".map(h => h.innerText.trim());"
                        + "return JSON.stringify(visible) === JSON.stringify(expected);"
                        + "}",
                List.of("Rose", "Chrysanthemum", "Tulip", "Sunflower", "Daisy"));
    }

    public void selectInStockOnly() {
        page.getByRole(AriaRole.CHECKBOX,
                new Page.GetByRoleOptions().setName("In Stock Only")).check();
        waitForFlowerHidden("Lily");
    }

    public List<String> getVisibleFlowers() {
        List<String> visibleFlowers = new ArrayList<>();
        for (Locator heading : page.locator("h3").all()) {
            String name = heading.innerText().trim();
            if (FLOWERS.contains(name) && heading.isVisible()) {
                visibleFlowers.add(name);
            }
        }
        return visibleFlowers;
    }

    private Locator flowerHeading(String flower) {
        return page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName(flower).setExact(true));
    }
}
