package com.dzollo.hw4.model;

import java.util.Objects;

/**
 * Паттерн Value Object: неизменяемая пара логин/пароль, равенство по значению.
 */
public final class Credentials {
    private final String username;
    private final String password;

    public Credentials(final String username, final String password) {
        this.username = Objects.requireNonNull(username, "username");
        this.password = Objects.requireNonNull(password, "password");
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Credentials)) {
            return false;
        }
        final Credentials other = (Credentials) o;
        return username.equals(other.username) && password.equals(other.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "Credentials{username='" + username + "', password='***'}";
    }
}