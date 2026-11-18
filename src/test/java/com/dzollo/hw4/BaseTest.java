package com.dzollo.hw4;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.Timeout;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

/**
 * Базовый класс тестов, безопасный для параллельного запуска.
 * Playwright-объекты не потокобезопасны, поэтому у каждого теста СВОИ
 * Playwright, Browser, Context и Page
 * (JUnit создаёт отдельный экземпляр класса на каждый тест, поля — не static).
 */
@Timeout(value = 120, unit = TimeUnit.SECONDS)
public abstract class BaseTest {
    private static final Path ARTIFACTS = Paths.get("build", "playwright-artifacts");
    private static final String APP_ORIGIN = "https://the-internet.herokuapp.com";

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(ARTIFACTS);
        playwright = Playwright.create();

        final boolean headed = Boolean.parseBoolean(System.getProperty("headed", "false"));
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(!headed));

        context = browser.newContext();
        // изоляция от сторонних ресурсов (шрифты, аналитика, бейджи): они не нужны
        // тестам и могут тормозить загрузку
        context.route("**/*", route -> {
            final String url = route.request().url();
            if (isAppOrLocal(url)) {
                route.resume();
            } else {
                System.out.println("[blocked third-party] " + url);
                route.abort();
            }
        });
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page = context.newPage();
        page.setDefaultTimeout(60_000);
        page.setDefaultNavigationTimeout(60_000);

        // диагностика: что именно ломается при загрузке страницы (видно в stdout отчёта
        // Gradle)
        page.onRequestFailed(request -> System.out.println(
                "[request failed] " + request.url() + " -> " + request.failure()));
        page.onConsoleMessage(message -> {
            if ("error".equals(message.type())) {
                System.out.println("[console error] " + message.text());
            }
        });
        page.onPageError(error -> System.out.println("[page error] " + error));
    }

    private static boolean isAppOrLocal(final String url) {
        return url.startsWith(APP_ORIGIN)
                || url.startsWith("data:")
                || url.startsWith("blob:")
                || url.startsWith("about:");
    }

    @AfterEach
    void tearDown(final TestInfo testInfo) {
        try {
            if (context != null) {
                try {
                    context.tracing().stop(new Tracing.StopOptions()
                            .setPath(ARTIFACTS.resolve(traceName(testInfo) + ".zip")));
                } finally {
                    context.close();
                }
            }
        } finally {
            try {
                if (browser != null) {
                    browser.close();
                }
            } finally {
                if (playwright != null) {
                    playwright.close();
                }
            }
        }
    }

    /**
     * Уникальное имя трейса: класс + метод + хеш displayName (иначе кириллица даёт
     * одинаковые "____").
     */
    private static String traceName(final TestInfo testInfo) {
        final String className = testInfo.getTestClass().map(Class::getSimpleName).orElse("Test");
        final String methodName = testInfo.getTestMethod().map(Method::getName).orElse("method");
        final String display = testInfo.getDisplayName().replaceAll("[^a-zA-Z0-9-_]", "_");
        final String shortDisplay = display.length() > 40 ? display.substring(0, 40) : display;
        return className + "_" + methodName + "_" + shortDisplay + "_"
                + Integer.toHexString(testInfo.getDisplayName().hashCode());
    }

    protected final Page page() {
        if (page == null) {
            throw new IllegalStateException("Browser page has not been initialized.");
        }
        return page;
    }
}