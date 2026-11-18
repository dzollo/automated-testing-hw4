package com.dzollo.hw4.elements;

import com.microsoft.playwright.Locator;

public class Select extends Element {

    public Select(final Locator locator, final String name) {
        super(locator, name);
    }

    public void selectByValue(final String value) {
        locator().selectOption(value);
    }

    public String selectedValue() {
        return locator().inputValue();
    }

    public String selectedText() {
        return locator().locator("option:checked").innerText().trim();
    }
}