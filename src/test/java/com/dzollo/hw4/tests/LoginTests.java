package com.dzollo.hw4.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dzollo.hw4.BaseTest;
import com.dzollo.hw4.pages.LoginPage;
import com.dzollo.hw4.pages.SecureAreaPage;

class LoginTests extends BaseTest {
    private static final String VALID_USERNAME = "tomsmith";
    private static final String VALID_PASSWORD = "SuperSecretPassword!";

    @Test
    @DisplayName("Login: успешный вход в secure area")
    void shouldLoginWithValidCredentials() {
        final LoginPage loginPage = new LoginPage(page()).open();
        loginPage.login(VALID_USERNAME, VALID_PASSWORD);

        final SecureAreaPage secureAreaPage = new SecureAreaPage(page());

        assertTrue(secureAreaPage.isOpened(),
                "Secure Area должна быть отображена после логина");
        assertTrue(secureAreaPage.successMessage().contains("You logged into a secure area!"),
                "Должно отображаться success-сообщение");
    }

    @Test
    @DisplayName("Login: ошибка при неверных учётных данных")
    void shouldShowErrorForInvalidCredentials() {
        final LoginPage loginPage = new LoginPage(page()).open();
        loginPage.login("wrong_user", "wrong_password");

        assertTrue(loginPage.isFlashMessageVisible(),
                "Flash-сообщение должно появиться после неудачного логина");
        assertTrue(loginPage.flashMessage().contains("Your username is invalid!"),
                "Должно отображаться сообщение о неверном username");
    }

    @Test
    @DisplayName("Login: ошибка при пустых учётных данных")
    void shouldShowErrorForEmptyCredentials() {
        final LoginPage loginPage = new LoginPage(page()).open();
        loginPage.login("", "");

        assertTrue(loginPage.flashMessage().contains("Your username is invalid!"),
                "Пустой username должен считаться невалидным");
    }
}