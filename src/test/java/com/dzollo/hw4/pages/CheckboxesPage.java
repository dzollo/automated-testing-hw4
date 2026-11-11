package com.dzollo.hw4.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class CheckboxesPage extends BasePage {
    private final Locator firstCheckbox;
    private final Locator secondCheckbox;

    public CheckboxesPage(final Page page) {
        super(page);
        final Locator checkboxes = page.getByRole(AriaRole.CHECKBOX);
        firstCheckbox = checkboxes.nth(0);
        secondCheckbox = checkboxes.nth(1);
    }

    public CheckboxesPage open() {
        openPath("/checkboxes");
        return this;
    }

    public boolean isFirstCheckboxChecked() {
        return firstCheckbox.isChecked();
    }

    public boolean isSecondCheckboxChecked() {
        return secondCheckbox.isChecked();
    }

    public void checkFirstCheckbox() {
        firstCheckbox.check();
    }

    public void uncheckSecondCheckbox() {
        secondCheckbox.uncheck();
    }
}