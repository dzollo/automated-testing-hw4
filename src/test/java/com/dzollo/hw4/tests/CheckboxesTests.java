package com.dzollo.hw4.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import com.dzollo.hw4.BaseTest;
import com.dzollo.hw4.pages.CheckboxesPage;

@Tag("checkboxes")
@DisplayName("Checkboxes")
class CheckboxesTests extends BaseTest {

        @Test
        @Tag("smoke")
        @Timeout(90)
        @DisplayName("Начальное состояние чекбоксов")
        void shouldHaveDefaultState() {
                final CheckboxesPage checkboxesPage = new CheckboxesPage(page()).get();

                assertAll("Начальное состояние",
                                () -> assertFalse(checkboxesPage.isChecked(0),
                                                "Первый чекбокс должен быть изначально не выбран"),
                                () -> assertTrue(checkboxesPage.isChecked(1),
                                                "Второй чекбокс должен быть изначально выбран"));
        }

        @Test
        @Tag("regression")
        @DisplayName("Выбрать первый и снять выбор со второго")
        void shouldCheckFirstAndUncheckSecondCheckbox() {
                final CheckboxesPage checkboxesPage = new CheckboxesPage(page()).get()
                                .check(0)
                                .uncheck(1);

                assertAll("Состояние после переключения",
                                () -> assertTrue(checkboxesPage.isChecked(0),
                                                "Первый чекбокс должен стать выбранным"),
                                () -> assertFalse(checkboxesPage.isChecked(1),
                                                "Второй чекбокс должен стать не выбранным"));
        }

        @TestFactory
        @Execution(ExecutionMode.SAME_THREAD) // динамические тесты делят одну page -> только последовательно
        @Tag("regression")
        @DisplayName("Каждый чекбокс переключается в противоположное состояние")
        Stream<DynamicTest> shouldToggleEachCheckbox() {
                final int total = new CheckboxesPage(page()).get().count();

                return IntStream.range(0, total)
                                .mapToObj(index -> dynamicTest("Переключение чекбокса №" + (index + 1), () -> {
                                        final CheckboxesPage checkboxesPage = new CheckboxesPage(page()).get();
                                        final boolean initial = checkboxesPage.isChecked(index);

                                        checkboxesPage.toggle(index);

                                        assertEquals(!initial, checkboxesPage.isChecked(index),
                                                        "Состояние чекбокса должно измениться на противоположное");
                                }));
        }
}