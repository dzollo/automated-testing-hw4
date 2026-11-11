package com.dzollo.hw4.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.dzollo.hw4.BaseTest;
import com.dzollo.hw4.pages.LoginPage;
import com.dzollo.hw4.pages.SecureAreaPage;

@Tag("login")
@DisplayName("Login")
class LoginTests extends BaseTest {
        private static final String VALID_USERNAME = "tomsmith";
        private static final String VALID_PASSWORD = "SuperSecretPassword!";

        @Nested
        @DisplayName("Позитивные сценарии")
        class PositiveScenarios {

                @Test
                @Tag("smoke")
                @Timeout(20)
                @DisplayName("Успешный вход в secure area")
                void shouldLoginWithValidCredentials() {
                        final LoginPage loginPage = new LoginPage(page()).open();
                        loginPage.login(VALID_USERNAME, VALID_PASSWORD);

                        final SecureAreaPage secureAreaPage = new SecureAreaPage(page());

                        assertAll("Результат успешного логина",
                                        () -> assertTrue(secureAreaPage.isOpened(),
                                                        "Secure Area должна быть отображена после логина"),
                                        () -> assertTrue(
                                                        secureAreaPage.successMessage()
                                                                        .contains("You logged into a secure area!"),
                                                        "Должно отображаться success-сообщение"));
                }
        }

        @Nested
        @DisplayName("Негативные сценарии")
        class NegativeScenarios {

                @Tag("regression")
                @ParameterizedTest(name = "username={0}, password={1} -> {2}")
                @CsvSource({
                                "wrong_user, wrong_password, Your username is invalid!",
                                "wrong_user, SuperSecretPassword!, Your username is invalid!",
                                "tomsmith, wrong_password, Your password is invalid!",
                                "'', '', Your username is invalid!"
                })
                void shouldShowErrorForInvalidCredentials(final String username,
                                final String password,
                                final String expectedMessage) {
                        final LoginPage loginPage = new LoginPage(page()).open();
                        loginPage.login(username, password);

                        assertAll("Ошибка авторизации",
                                        () -> assertTrue(loginPage.isFlashMessageVisible(),
                                                        "Flash-сообщение должно появиться после неудачного логина"),
                                        () -> assertTrue(loginPage.flashMessage().contains(expectedMessage),
                                                        "Ожидалось сообщение: " + expectedMessage));
                }

                @Test
                @Disabled("Демонстрация @Disabled: на сайте нет блокировки после нескольких неудачных попыток")
                @DisplayName("Блокировка после нескольких неудачных попыток")
                void shouldBlockAfterSeveralFailedAttempts() {
                        // не реализовано: функциональность отсутствует в приложении
                }
        }
}