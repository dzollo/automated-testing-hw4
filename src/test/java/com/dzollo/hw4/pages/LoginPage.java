package com.dzollo.hw4.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage {
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator flashMessage;

    public LoginPage(final Page page) {
        super(page);
        usernameInput = page.getByLabel("Username");
        passwordInput = page.getByLabel("Password");
        loginButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Login"));
        flashMessage = page.locator("#flash");
    }

    public LoginPage open() {
        openPath("/login");
        return this;
    }

    public void login(final String username, final String password) {
        usernameInput.fill(username);
        passwordInput.fill(password);
        loginButton.click();
    }

    public String flashMessage() {
        flashMessage.waitFor();
        return flashMessage.innerText().trim();
    }

    public boolean isFlashMessageVisible() {
        return flashMessage.isVisible();
    }
}