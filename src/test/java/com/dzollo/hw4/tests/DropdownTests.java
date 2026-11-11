package com.dzollo.hw4.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.dzollo.hw4.BaseTest;
import com.dzollo.hw4.pages.DropdownPage;

@Tag("dropdown")
@DisplayName("Dropdown")
class DropdownTests extends BaseTest {

    @Test
    @Tag("smoke")
    @DisplayName("По умолчанию выбран плейсхолдер")
    void shouldHavePlaceholderSelectedByDefault() {
        final DropdownPage dropdownPage = new DropdownPage(page()).open();

        assertAll("Начальное состояние dropdown",
                () -> assertEquals("", dropdownPage.selectedValue(),
                        "Value плейсхолдера должно быть пустым"),
                () -> assertEquals("Please select an option", dropdownPage.selectedOptionText(),
                        "Должен быть выбран плейсхолдер"));
    }

    @Tag("regression")
    @Timeout(20)
    @ParameterizedTest(name = "Выбор value={0} -> текст {1}")
    @CsvSource({
            "1, Option 1",
            "2, Option 2"
    })
    void shouldSelectOption(final String value, final String expectedText) {
        final DropdownPage dropdownPage = new DropdownPage(page()).open();

        dropdownPage.selectOption(value);

        assertAll("Выбранный пункт",
                () -> assertEquals(value, dropdownPage.selectedValue(),
                        "Value выбранного пункта не совпадает"),
                () -> assertEquals(expectedText, dropdownPage.selectedOptionText(),
                        "Текст выбранного пункта не совпадает"));
    }
}