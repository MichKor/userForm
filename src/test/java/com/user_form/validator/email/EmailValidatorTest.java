package com.user_form.validator.email;

import exception.EmailValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {

    private EmailValidator emailValidator;

    @BeforeEach
    void setUp() {
        emailValidator = new EmailValidator();
    }

    private static Stream<String> correctEmails() {
        return Stream.of(
                "michal.korwel@gmail.com",
                "korwel.michal@gmail.com",
                "jan.korwel@wp.pl",
                "anna.woznia@one.pl"
        );
    }

    @ParameterizedTest
    @MethodSource("correctEmails")
    void shouldReturnTrueForCorrectEmails(String emailAddress) {
        //given
        //when
        final boolean valid = emailValidator.validate(emailAddress);
        //then
        assertTrue(valid, "Should return true for correct email.");
    }

    private static Stream<String> inCorrectEmails() {
        return Stream.of(
                "michal.korwelgmail.com",
                "korwel.michal@gmail.",
                "jan.korwel@",
                "@one.pl"
        );
    }

    @ParameterizedTest
    @MethodSource("inCorrectEmails")
    void shouldReturnEmailValidationExceptionForIncorrectEmails(String emailAddress) {
        //given
        //when
        //then
        assertThrows(EmailValidationException.class, () -> emailValidator.validate(emailAddress));
    }

    private static Stream<String> nullOrEmptyOrBlankEmails() {
        return Stream.of(
                "",
                " ",
                null
        );
    }

    @ParameterizedTest
    @MethodSource("nullOrEmptyOrBlankEmails")
    void shouldReturnFalseForIncorrectEmails(String emailAddress) {
        //given
        //when
        final boolean valid = emailValidator.validate(emailAddress);
        //then
        assertFalse(valid, "Should return false for correct email.");
    }
}
