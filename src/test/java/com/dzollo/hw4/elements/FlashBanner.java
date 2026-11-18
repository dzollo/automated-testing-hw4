package com.dzollo.hw4.elements;

import com.dzollo.hw4.model.FlashMessage;
import com.microsoft.playwright.Locator;

/**
 * Элемент flash-сообщения: умеет превращать себя в Value Object
 * {@link FlashMessage}.
 */
public class FlashBanner extends Element {
    private static final String CLOSE_SYMBOL = "\u00D7";

    public FlashBanner(final Locator locator, final String name) {
        super(locator, name);
    }

    public FlashMessage read() {
        waitUntilVisible();
        final String cssClass = locator().getAttribute("class");
        final String text = locator().innerText().replace(CLOSE_SYMBOL, "").trim();
        return new FlashMessage(typeOf(cssClass), text);
    }

    private static FlashMessage.Type typeOf(final String cssClass) {
        if (cssClass == null) {
            return FlashMessage.Type.UNKNOWN;
        }
        if (cssClass.contains("success")) {
            return FlashMessage.Type.SUCCESS;
        }
        if (cssClass.contains("error")) {
            return FlashMessage.Type.ERROR;
        }
        return FlashMessage.Type.UNKNOWN;
    }
}