package com.user_form.validator.phone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PhoneNumberValidatorTest {

    private PhoneNumberValidator phoneNumberValidator;

    @BeforeEach
    void setUp() {
        phoneNumberValidator = new PhoneNumberValidator();
    }

    private static Stream<String> correctPhoneNumbers() {
        return Stream.of(
                "500500500",
                "123435789",
                "222222222",
                "223332222"
        );
    }

    @ParameterizedTest
    @MethodSource("correctPhoneNumbers")
    void shouldReturnTrueForCorrectPhoneNumbers(String phoneNumber) {
        //given
        //when
        final boolean valid = phoneNumberValidator.phoneNumberValidation(phoneNumber);
        //then
        assertTrue(valid, "Should return true for correct phone number.");
    }

    @Test
    void shouldReturnFalseForToShortNumber() {
        //given
        final String tooShortNumber = "50050050";
        //when
        final boolean valid = phoneNumberValidator.phoneNumberValidation(tooShortNumber);
        //then
        assertFalse(valid, "Should return false for too short value");
    }

    @Test
    void shouldReturnFalseForToLongNumber() {
        //given
        //when
        final String tooLongNumber = "5005005000";
        final boolean valid = phoneNumberValidator.phoneNumberValidation(tooLongNumber);
        //then
        assertFalse(valid, "Should return false for too long value");
    }

    @Test
    void shouldReturnFalseForNumberStartingWithZero() {
        //given
        final String startingWithZero = "050500500";
        //when
        final boolean valid = phoneNumberValidator.phoneNumberValidation(startingWithZero);
        //then
        assertFalse(valid, "Should return false for numbers starting with 0.");
    }

    private static Stream<String> numbersWithNotOnlyDigits() {
        return Stream.of(
                "5005005a0",
                "1234357x9",
                "22222_222",
                "22222 222",
                "22-222222",
                ".22222222",
                "222222/22",
                "2233x2222"
        );
    }

    @ParameterizedTest
    @MethodSource("numbersWithNotOnlyDigits")
    void shouldReturnFalseForNumberWithNotOnlyDigits(String phoneNumber) {
        //given
        final String startingWithZero = "050500500";
        //when
        final boolean valid = phoneNumberValidator.phoneNumberValidation(startingWithZero);
        //then
        assertFalse(valid, "Should return false for  phone number containing not only digits");
    }

}
