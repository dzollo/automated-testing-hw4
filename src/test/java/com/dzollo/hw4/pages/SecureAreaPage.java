package com.dzollo.hw4.pages;

import com.dzollo.hw4.elements.Button;
import com.dzollo.hw4.elements.FlashBanner;
import com.dzollo.hw4.model.FlashMessage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SecureAreaPage extends BasePage<SecureAreaPage> {
    private static final String PATH = "/secure";

    private final Locator heading;
    private final Button logoutButton;
    private final FlashBanner flash;

    public SecureAreaPage(final Page page) {
        super(page);
        heading = page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions()
                        .setName("Secure Area")
                        .setLevel(2));
        logoutButton = new Button(page.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Logout")), "Logout");
        flash = new FlashBanner(page.locator("#flash"), "Flash");
    }

    /**
     * Напрямую страницу не открыть (нужен логин), поэтому load() только ждёт
     * переход.
     */
    @Override
    protected void load() {
        page().waitForURL("**" + PATH);
    }

    @Override
    protected void isLoaded() {
        require(isCurrentUrlContains(PATH), "Secure Area не открыта, url: " + currentUrl());
        heading.waitFor();
    }

    public boolean isOpened() {
        return isCurrentUrlContains(PATH) && heading.isVisible();
    }

    public FlashMessage flashMessage() {
        return flash.read();
    }

    public LoginPage logout() {
        logoutButton.click();
        return new LoginPage(page()).get();
    }
}