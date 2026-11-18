package com.dzollo.hw4.elements;

import com.microsoft.playwright.Locator;

/**
 * Паттерн Page Element / Wrapper.
 * Обёртка над Locator: хранит имя элемента и добавляет общие операции.
 */
public abstract class Element {
    private final Locator locator;
    private final String name;

    protected Element(final Locator locator, final String name) {
        this.locator = locator;
        this.name = name;
    }

    protected final Locator locator() {
        return locator;
    }

    public final String name() {
        return name;
    }

    public boolean isVisible() {
        return locator.isVisible();
    }

    public void waitUntilVisible() {
        locator.waitFor();
    }

    public String text() {
        waitUntilVisible();
        return locator.innerText().trim();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + name + "]";
    }
}