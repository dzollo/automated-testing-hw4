package com.dzollo.hw4.core;

/**
 * Паттерн Loadable Component.
 * Страница сама умеет загружать себя (load) и проверять, что она загружена
 * (isLoaded).
 * Метод get() гарантирует, что страница в нужном состоянии, и возвращает её для
 * цепочки вызовов.
 */
public abstract class LoadableComponent<T extends LoadableComponent<T>> {

    @SuppressWarnings("unchecked")
    public T get() {
        try {
            isLoaded();
            return (T) this;
        } catch (AssertionError ignored) {
            load();
        }
        isLoaded();
        return (T) this;
    }

    /** Загружает страницу (навигация, ожидание перехода и т.п.). */
    protected abstract void load();

    /** Бросает AssertionError, если страница не в ожидаемом состоянии. */
    protected abstract void isLoaded() throws AssertionError;
}