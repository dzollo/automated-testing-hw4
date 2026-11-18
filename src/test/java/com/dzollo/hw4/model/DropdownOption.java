package com.dzollo.hw4.model;

import java.util.Arrays;

/**
 * Value Object (enum) для пунктов выпадающего списка: value + отображаемый
 * текст.
 */
public enum DropdownOption {
    PLACEHOLDER("", "Please select an option"),
    OPTION_1("1", "Option 1"),
    OPTION_2("2", "Option 2");

    private final String value;
    private final String text;

    DropdownOption(final String value, final String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public static DropdownOption fromValue(final String value) {
        return Arrays.stream(values())
                .filter(option -> option.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Неизвестное value: " + value));
    }
}