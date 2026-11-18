package com.dzollo.hw4.elements;

import com.microsoft.playwright.Locator;

public class Input extends Element {

    public Input(final Locator locator, final String name) {
        super(locator, name);
    }

    public void fill(final String text) {
        locator().fill(text);
    }

    public String value() {
        return locator().inputValue();
    }
}