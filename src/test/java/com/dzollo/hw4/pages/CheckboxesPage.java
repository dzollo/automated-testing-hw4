package com.dzollo.hw4.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CheckboxesPage extends BasePage {
    private final Locator checkboxes;

    public CheckboxesPage(final Page page) {
        super(page);
        checkboxes = page.getByRole(AriaRole.CHECKBOX);
    }

    public CheckboxesPage open() {
        openPath("/checkboxes");
        return this;
    }

    public int count() {
        return checkboxes.count();
    }

    public boolean isChecked(final int index) {
        return checkboxes.nth(index).isChecked();
    }

    public void check(final int index) {
        checkboxes.nth(index).check();
    }

    public void uncheck(final int index) {
        checkboxes.nth(index).uncheck();
    }
}