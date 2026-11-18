package com.dzollo.hw4.pages;

import com.dzollo.hw4.elements.Select;
import com.dzollo.hw4.model.DropdownOption;
import com.microsoft.playwright.Page;

public class DropdownPage extends BasePage<DropdownPage> {
    private static final String PATH = "/dropdown";

    private final Select dropdown;

    public DropdownPage(final Page page) {
        super(page);
        dropdown = new Select(page.locator("#dropdown"), "Dropdown");
    }

    @Override
    protected void load() {
        openPath(PATH);
    }

    @Override
    protected void isLoaded() {
        require(isCurrentUrlContains(PATH), "Страница dropdown не открыта, url: " + currentUrl());
        dropdown.waitUntilVisible();
    }

    public DropdownPage select(final DropdownOption option) {
        dropdown.selectByValue(option.getValue());
        return this;
    }

    public DropdownOption selectedOption() {
        return DropdownOption.fromValue(dropdown.selectedValue());
    }

    public String selectedValue() {
        return dropdown.selectedValue();
    }

    public String selectedOptionText() {
        return dropdown.selectedText();
    }
}