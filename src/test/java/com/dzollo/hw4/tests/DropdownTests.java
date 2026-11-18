package com.dzollo.hw4.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.dzollo.hw4.BaseTest;
import com.dzollo.hw4.model.DropdownOption;
import com.dzollo.hw4.pages.DropdownPage;

@Tag("dropdown")
@DisplayName("Dropdown")
class DropdownTests extends BaseTest {

        @Test
        @Tag("smoke")
        @DisplayName("По умолчанию выбран плейсхолдер")
        void shouldHavePlaceholderSelectedByDefault() {
                final DropdownPage dropdownPage = new DropdownPage(page()).get();

                assertAll("Начальное состояние dropdown",
                                () -> assertEquals(DropdownOption.PLACEHOLDER, dropdownPage.selectedOption(),
                                                "Должен быть выбран плейсхолдер"),
                                () -> assertEquals(DropdownOption.PLACEHOLDER.getText(),
                                                dropdownPage.selectedOptionText(),
                                                "Текст плейсхолдера не совпадает"));
        }

        @Tag("regression")
        @Timeout(90)
        @ParameterizedTest(name = "Выбор {0}")
        @EnumSource(value = DropdownOption.class, names = { "OPTION_1", "OPTION_2" })
        void shouldSelectOption(final DropdownOption option) {
                final DropdownPage dropdownPage = new DropdownPage(page()).get()
                                .select(option);

                assertAll("Выбранный пункт",
                                () -> assertEquals(option, dropdownPage.selectedOption(),
                                                "Выбранный пункт не совпадает"),
                                () -> assertEquals(option.getText(), dropdownPage.selectedOptionText(),
                                                "Текст выбранного пункта не совпадает"));
        }
}