package com.dzollo.hw4.pages;

import com.dzollo.hw4.core.LoadableComponent;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;

/**
 * Базовый Page Object. Наследует Loadable Component, T — тип конкретной
 * страницы (для цепочек вызовов).
 */
public abstract class BasePage<T extends BasePage<T>> extends LoadableComponent<T> {
    private static final String BASE_URL = "https://the-internet.herokuapp.com";

    private final Page page;

    protected BasePage(final Page page) {
        this.page = page;
    }

    protected final Page page() {
        return page;
    }

    protected final void openPath(final String path) {
        page.navigate(BASE_URL + path, new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.COMMIT));
    }

    protected final String currentUrl() {
        return page.url();
    }

    protected final boolean isCurrentUrlContains(final String fragment) {
        return currentUrl().contains(fragment);
    }

    protected static void require(final boolean condition, final String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}