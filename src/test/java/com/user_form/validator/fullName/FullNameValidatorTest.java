package com.user_form.validator.fullName;

import exception.UserValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class FullNameValidatorTest {

    private FullNameValidator fullNameValidator;

    @BeforeEach
    void setUp() {
        fullNameValidator = new FullNameValidator();
    }

    private static Stream<Arguments> correctFullNames() {
        return Stream.of(
               Arguments.of("Michał", "Korwel"),
                Arguments.of("Adrian", "Kowal")
        );
    }

    @ParameterizedTest
    @MethodSource("correctFullNames")
    void shouldReturnTrueForCorrectEmails(String firstName, String lastName) {
        //given
        //when
        final boolean valid = fullNameValidator.validateFullName(firstName, lastName);
        //then
        assertTrue(valid, "Should return true for correct full name.");
    }

    private static Stream<Arguments> inCorrectFullNames() {
        return Stream.of(
                Arguments.of("", "Korwel"),
                Arguments.of("", "Kowal"),
                Arguments.of(null, "Kowal"),
                Arguments.of(null, null),
                Arguments.of("", ""),
                Arguments.of(" ", " "),
                Arguments.of("Michał", ""),
                Arguments.of("Michał", " "),
                Arguments.of("Michał", null)
        );
    }

    @ParameterizedTest
    @MethodSource("inCorrectFullNames")
    void shouldReturnFalseForInCorrectEmails(String firstName, String lastName) {
        //given
        //when
        //then
        assertThrows(UserValidationException.class, () -> fullNameValidator.validateFullName(firstName, lastName));
    }
}
