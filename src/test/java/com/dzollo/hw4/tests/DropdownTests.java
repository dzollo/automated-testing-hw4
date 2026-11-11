package com.dzollo.hw4.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dzollo.hw4.BaseTest;
import com.dzollo.hw4.pages.DropdownPage;

class DropdownTests extends BaseTest {

    @Test
    @DisplayName("Dropdown: выбор Option 2 и проверка значения/текста")
    void shouldSelectOptionTwoFromDropdown() {
        final DropdownPage dropdownPage = new DropdownPage(page()).open();

        dropdownPage.selectOption("2");

        assertEquals("2", dropdownPage.selectedValue(),
                "Value выбранного пункта должно быть '2'");
        assertEquals("Option 2", dropdownPage.selectedOptionText(),
                "Текст выбранного пункта должен быть 'Option 2'");
    }
}