package com.dzollo.hw4.data;

import java.util.UUID;

import com.dzollo.hw4.model.Credentials;

/**
 * Паттерн Factory: создаёт разные варианты учётных данных для сценариев логина.
 */
public final class CredentialsFactory {

    private CredentialsFactory() {
    }

    public static Credentials valid() {
        return TestDataRegistry.validCredentials();
    }

    public static Credentials unknownUserAndPassword() {
        return new Credentials("user_" + suffix(), "pwd_" + suffix());
    }

    public static Credentials unknownUserWithValidPassword() {
        return new Credentials("user_" + suffix(), valid().getPassword());
    }

    public static Credentials validUserWithWrongPassword() {
        return new Credentials(valid().getUsername(), "pwd_" + suffix());
    }

    public static Credentials empty() {
        return new Credentials("", "");
    }

    private static String suffix() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}