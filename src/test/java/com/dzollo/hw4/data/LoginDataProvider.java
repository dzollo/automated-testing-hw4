package com.dzollo.hw4.data;

import static com.dzollo.hw4.data.TestDataRegistry.message;
import static com.dzollo.hw4.data.TestDataRegistry.MessageKey.INVALID_PASSWORD;
import static com.dzollo.hw4.data.TestDataRegistry.MessageKey.INVALID_USERNAME;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

/**
 * Паттерн Data Provider: поставляет наборы данных (имя сценария, credentials,
 * ожидаемое сообщение).
 */
public final class LoginDataProvider {

    private LoginDataProvider() {
    }

    public static Stream<Arguments> invalidCredentials() {
        return Stream.of(
                Arguments.of("Неизвестный пользователь и пароль",
                        CredentialsFactory.unknownUserAndPassword(), message(INVALID_USERNAME)),
                Arguments.of("Неизвестный пользователь, верный пароль",
                        CredentialsFactory.unknownUserWithValidPassword(), message(INVALID_USERNAME)),
                Arguments.of("Верный пользователь, неверный пароль",
                        CredentialsFactory.validUserWithWrongPassword(), message(INVALID_PASSWORD)),
                Arguments.of("Пустые логин и пароль",
                        CredentialsFactory.empty(), message(INVALID_USERNAME)));
    }
}