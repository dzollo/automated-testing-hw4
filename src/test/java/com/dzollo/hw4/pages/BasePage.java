package com.dzollo.hw4.pages;

import com.microsoft.playwright.Page;

public abstract class BasePage {
    private static final String BASE_URL = "https://the-internet.herokuapp.com";

    private final Page page;

    protected BasePage(final Page page) {
        this.page = page;
    }

    protected final Page page() {
        return page;
    }

    protected final void openPath(final String path) {
        page.navigate(BASE_URL + path);
    }

    protected final String currentUrl() {
        return page.url();
    }

    protected final boolean isCurrentUrlContains(final String fragment) {
        return currentUrl().contains(fragment);
    }
}