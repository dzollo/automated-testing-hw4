package com.dzollo.hw4.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SecureAreaPage extends BasePage {
    private final Locator heading;
    private final Locator flashMessage;

    public SecureAreaPage(final Page page) {
        super(page);
        heading = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions()
                        .setName("Secure Area")
                        .setLevel(2));
        flashMessage = page.locator("#flash");
    }

    public boolean isOpened() {
        return isCurrentUrlContains("/secure") && heading.isVisible();
    }

    public String successMessage() {
        flashMessage.waitFor();
        return flashMessage.innerText().trim();
    }
}