package com.dzollo.hw4.pages;

import com.dzollo.hw4.elements.Button;
import com.dzollo.hw4.elements.FlashBanner;
import com.dzollo.hw4.elements.Input;
import com.dzollo.hw4.model.Credentials;
import com.dzollo.hw4.model.FlashMessage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage extends BasePage<LoginPage> {
    private static final String PATH = "/login";

    private final Input usernameInput;
    private final Input passwordInput;
    private final Button loginButton;
    private final FlashBanner flash;

    public LoginPage(final Page page) {
        super(page);
        usernameInput = new Input(page.getByLabel("Username"), "Username");
        passwordInput = new Input(page.getByLabel("Password"), "Password");
        loginButton = new Button(page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Login")), "Login");
        flash = new FlashBanner(page.locator("#flash"), "Flash");
    }

    @Override
    protected void load() {
        openPath(PATH);
    }

    @Override
    protected void isLoaded() {
        require(isCurrentUrlContains(PATH), "Страница логина не открыта, url: " + currentUrl());
        loginButton.waitUntilVisible();
    }

    // ---- Chain of invocations ----

    public LoginPage withUsername(final String username) {
        usernameInput.fill(username);
        return this;
    }

    public LoginPage withPassword(final String password) {
        passwordInput.fill(password);
        return this;
    }

    public LoginPage with(final Credentials credentials) {
        return withUsername(credentials.getUsername())
                .withPassword(credentials.getPassword());
    }

    /** Успешный логин: переходим на SecureAreaPage. */
    public SecureAreaPage submit() {
        loginButton.click();
        return new SecureAreaPage(page()).get();
    }

    /** Неудачный логин: остаёмся на LoginPage. */
    public LoginPage submitExpectingFailure() {
        loginButton.click();
        return get();
    }

    public SecureAreaPage loginAs(final Credentials credentials) {
        return with(credentials).submit();
    }

    public LoginPage loginWithInvalid(final Credentials credentials) {
        return with(credentials).submitExpectingFailure();
    }

    public FlashMessage flashMessage() {
        return flash.read();
    }
}