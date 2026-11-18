package com.dzollo.hw4.data;

import java.util.EnumMap;
import java.util.Map;

import com.dzollo.hw4.model.Credentials;
import com.dzollo.hw4.model.FlashMessage;

/**
 * Паттерн Data Registry: единое место, где хранятся эталонные тестовые данные
 * (валидный пользователь и ожидаемые сообщения приложения).
 */
public final class TestDataRegistry {

    public enum MessageKey {
        LOGIN_SUCCESS,
        LOGOUT_SUCCESS,
        INVALID_USERNAME,
        INVALID_PASSWORD
    }

    private static final Credentials VALID_CREDENTIALS = new Credentials("tomsmith", "SuperSecretPassword!");

    private static final Map<MessageKey, FlashMessage> MESSAGES = new EnumMap<>(MessageKey.class);

    static {
        MESSAGES.put(MessageKey.LOGIN_SUCCESS, FlashMessage.success("You logged into a secure area!"));
        MESSAGES.put(MessageKey.LOGOUT_SUCCESS, FlashMessage.success("You logged out of the secure area!"));
        MESSAGES.put(MessageKey.INVALID_USERNAME, FlashMessage.error("Your username is invalid!"));
        MESSAGES.put(MessageKey.INVALID_PASSWORD, FlashMessage.error("Your password is invalid!"));
    }

    private TestDataRegistry() {
    }

    public static Credentials validCredentials() {
        return VALID_CREDENTIALS;
    }

    public static FlashMessage message(final MessageKey key) {
        final FlashMessage message = MESSAGES.get(key);
        if (message == null) {
            throw new IllegalArgumentException("Сообщение не зарегистрировано: " + key);
        }
        return message;
    }
}