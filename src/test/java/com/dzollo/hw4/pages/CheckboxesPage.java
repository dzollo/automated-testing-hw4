package com.dzollo.hw4.pages;

import com.dzollo.hw4.elements.Checkbox;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CheckboxesPage extends BasePage<CheckboxesPage> {
    private static final String PATH = "/checkboxes";

    private final Locator checkboxes;

    public CheckboxesPage(final Page page) {
        super(page);
        checkboxes = page.getByRole(AriaRole.CHECKBOX);
    }

    @Override
    protected void load() {
        openPath(PATH);
    }

    @Override
    protected void isLoaded() {
        require(isCurrentUrlContains(PATH), "Страница чекбоксов не открыта, url: " + currentUrl());
        checkboxes.first().waitFor();
    }

    public int count() {
        return checkboxes.count();
    }

    public Checkbox checkbox(final int index) {
        return new Checkbox(checkboxes.nth(index), "Checkbox #" + (index + 1));
    }

    public boolean isChecked(final int index) {
        return checkbox(index).isChecked();
    }

    // ---- Chain of invocations ----

    public CheckboxesPage check(final int index) {
        checkbox(index).check();
        return this;
    }

    public CheckboxesPage uncheck(final int index) {
        checkbox(index).uncheck();
        return this;
    }

    public CheckboxesPage toggle(final int index) {
        checkbox(index).toggle();
        return this;
    }
}