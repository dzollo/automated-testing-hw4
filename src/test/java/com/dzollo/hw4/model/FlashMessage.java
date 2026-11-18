package com.dzollo.hw4.model;

import java.util.Objects;

/**
 * Паттерн Value Object: flash-сообщение (тип + текст), равенство по значению.
 */
public final class FlashMessage {

    public enum Type {
        SUCCESS, ERROR, UNKNOWN
    }

    private final Type type;
    private final String text;

    public FlashMessage(final Type type, final String text) {
        this.type = Objects.requireNonNull(type, "type");
        this.text = Objects.requireNonNull(text, "text");
    }

    public static FlashMessage success(final String text) {
        return new FlashMessage(Type.SUCCESS, text);
    }

    public static FlashMessage error(final String text) {
        return new FlashMessage(Type.ERROR, text);
    }

    public Type getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlashMessage)) {
            return false;
        }
        final FlashMessage other = (FlashMessage) o;
        return type == other.type && text.equals(other.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, text);
    }

    @Override
    public String toString() {
        return type + ": " + text;
    }
}