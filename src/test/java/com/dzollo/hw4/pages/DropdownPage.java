package com.dzollo.hw4.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DropdownPage extends BasePage {
    private final Locator dropdown;

    public DropdownPage(final Page page) {
        super(page);
        dropdown = page.locator("#dropdown");
    }

    public DropdownPage open() {
        openPath("/dropdown");
        return this;
    }

    public void selectOption(final String value) {
        dropdown.selectOption(value);
    }

    public String selectedValue() {
        return dropdown.inputValue();
    }

    public String selectedOptionText() {
        return dropdown.locator("option:checked")
                .innerText().trim();
    }
}