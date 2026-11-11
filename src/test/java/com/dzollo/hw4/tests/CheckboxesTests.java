package com.dzollo.hw4.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dzollo.hw4.BaseTest;
import com.dzollo.hw4.pages.CheckboxesPage;

class CheckboxesTests extends BaseTest {

    @Test
    @DisplayName("Checkbox: проверить переключение первого и второго чекбокса")
    void shouldCheckFirstAndUncheckSecondCheckbox() {
        final CheckboxesPage checkboxesPage = new CheckboxesPage(page()).open();

        assertFalse(checkboxesPage.isFirstCheckboxChecked(),
                "Первый чекбокс должен быть изначально не выбран");
        assertTrue(checkboxesPage.isSecondCheckboxChecked(),
                "Второй чекбокс должен быть изначально выбран");

        checkboxesPage.checkFirstCheckbox();
        checkboxesPage.uncheckSecondCheckbox();

        assertTrue(checkboxesPage.isFirstCheckboxChecked(),
                "Первый чекбокс должен стать выбранным");
        assertFalse(checkboxesPage.isSecondCheckboxChecked(),
                "Второй чекбокс должен стать не выбранным");
    }
}