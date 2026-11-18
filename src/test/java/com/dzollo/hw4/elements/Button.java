package com.dzollo.hw4.elements;

import com.microsoft.playwright.Locator;

public class Button extends Element {

    public Button(final Locator locator, final String name) {
        super(locator, name);
    }

    public void click() {
        locator().click();
    }
}