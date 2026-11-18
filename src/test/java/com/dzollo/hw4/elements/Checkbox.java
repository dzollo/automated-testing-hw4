package com.dzollo.hw4.elements;

import com.microsoft.playwright.Locator;

public class Checkbox extends Element {

    public Checkbox(final Locator locator, final String name) {
        super(locator, name);
    }

    public boolean isChecked() {
        return locator().isChecked();
    }

    public void check() {
        locator().check();
    }

    public void uncheck() {
        locator().uncheck();
    }

    public void toggle() {
        if (isChecked()) {
            uncheck();
        } else {
            check();
        }
    }
}