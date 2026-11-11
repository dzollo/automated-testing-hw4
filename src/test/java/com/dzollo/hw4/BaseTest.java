package com.dzollo.hw4;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.Timeout;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

@Timeout(value = 30, unit = TimeUnit.SECONDS)
public abstract class BaseTest {
    private static final Path ARTIFACTS = Paths.get("build", "playwright-artifacts");

    private static Playwright playwright;
    private static Browser browser;

    private BrowserContext context;
    private Page page;

    @BeforeAll
    static void launchBrowser() throws Exception {
        Files.createDirectories(ARTIFACTS);
        playwright = Playwright.create();

        final boolean headed = Boolean.parseBoolean(System.getProperty("headed", "false"));
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(!headed));
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @BeforeEach
    void createContext() {
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));

        page = context.newPage();
        page.setDefaultTimeout(15_000);
        page.setDefaultNavigationTimeout(15_000);
    }

    @AfterEach
    void saveTraceAndCloseContext(final TestInfo testInfo) {
        if (context == null) {
            return;
        }
        try {
            String traceName = testInfo.getDisplayName().replaceAll("[^a-zA-Z0-9-_]", "_");
            if (traceName.length() > 100) {
                traceName = traceName.substring(0, 100);
            }
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(ARTIFACTS.resolve(traceName + ".zip")));
        } finally {
            context.close();
        }
    }

    protected final Page page() {
        if (page == null) {
            throw new IllegalStateException("Browser page has not been initialized.");
        }
        return page;
    }
}