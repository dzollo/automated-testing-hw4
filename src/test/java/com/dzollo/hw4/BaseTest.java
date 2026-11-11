package com.dzollo.hw4;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

public abstract class BaseTest {
    private static final Path ARTIFACTS = Paths.get("build", "playwright-artifacts");

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeEach
    protected void setUpBrowser(final TestInfo testInfo) throws Exception {
        Files.createDirectories(ARTIFACTS);

        playwright = Playwright.create();

        final boolean headed = Boolean.parseBoolean(System.getProperty("headed", "false"));
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(!headed));

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
    protected void closeBrowser(final TestInfo testInfo) throws Exception {
        if (context != null) {
            final String traceName = testInfo.getDisplayName()
                    .replaceAll("[^a-zA-Z0-9-_]", "_") + ".zip";
            try {
                context.tracing().stop(new Tracing.StopOptions()
                        .setPath(ARTIFACTS.resolve(traceName)));
            } catch (final Exception ignored) {
                // ignore, I am a bad boy
            }
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    protected final Page page() {
        if (page == null) {
            throw new IllegalStateException("Browser page has not been initialized.");
        }
        return page;
    }
}