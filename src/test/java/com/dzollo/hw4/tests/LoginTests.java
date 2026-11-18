package com.dzollo.hw4.tests;

import static com.dzollo.hw4.data.TestDataRegistry.message;
import static com.dzollo.hw4.data.TestDataRegistry.MessageKey.LOGIN_SUCCESS;
import static com.dzollo.hw4.data.TestDataRegistry.MessageKey.LOGOUT_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.dzollo.hw4.BaseTest;
import com.dzollo.hw4.data.CredentialsFactory;
import com.dzollo.hw4.model.Credentials;
import com.dzollo.hw4.model.FlashMessage;
import com.dzollo.hw4.pages.LoginPage;
import com.dzollo.hw4.pages.SecureAreaPage;

@Tag("login")
@DisplayName("Login")
class LoginTests extends BaseTest {

        @Nested
        @DisplayName("Позитивные сценарии")
        class PositiveScenarios {

                @Test
                @Tag("smoke")
                @Timeout(90)
                @DisplayName("Успешный вход в secure area")
                void shouldLoginWithValidCredentials() {
                        final SecureAreaPage secureAreaPage = new LoginPage(page()).get()
                                        .loginAs(CredentialsFactory.valid());

                        assertAll("Результат успешного логина",
                                        () -> assertTrue(secureAreaPage.isOpened(),
                                                        "Secure Area должна быть отображена после логина"),
                                        () -> assertEquals(message(LOGIN_SUCCESS), secureAreaPage.flashMessage(),
                                                        "Должно отображаться success-сообщение"));
                }

                @Test
                @Tag("regression")
                @DisplayName("Вход и выход: цепочка login -> logout")
                void shouldLogoutAfterLogin() {
                        final LoginPage loginPage = new LoginPage(page()).get()
                                        .loginAs(CredentialsFactory.valid())
                                        .logout();

                        assertEquals(message(LOGOUT_SUCCESS), loginPage.flashMessage(),
                                        "После выхода должно отображаться сообщение о выходе");
                }
        }

        @Nested
        @DisplayName("Негативные сценарии")
        class NegativeScenarios {

                @Tag("regression")
                @ParameterizedTest(name = "{0}")
                @MethodSource("com.dzollo.hw4.data.LoginDataProvider#invalidCredentials")
                void shouldShowErrorForInvalidCredentials(final String scenario,
                                final Credentials credentials,
                                final FlashMessage expectedMessage) {
                        final LoginPage loginPage = new LoginPage(page()).get()
                                        .loginWithInvalid(credentials);

                        assertEquals(expectedMessage, loginPage.flashMessage(),
                                        "Сообщение об ошибке не совпадает для сценария: " + scenario);
                }

                @Test
                @Disabled("Демонстрация @Disabled: на сайте нет блокировки после нескольких неудачных попыток")
                @DisplayName("Блокировка после нескольких неудачных попыток")
                void shouldBlockAfterSeveralFailedAttempts() {
                        // не реализовано: функциональность отсутствует в приложении
                }
        }
}