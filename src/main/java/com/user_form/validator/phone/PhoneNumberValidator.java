package com.user_form.validator.phone;

import java.util.regex.Pattern;

public class PhoneNumberValidator {

    private static final Pattern NINE_DIGITS_FIRST_NOT_ZERO_PATTERN = Pattern.compile("^[1-9]\\d{8}$");

    boolean phoneNumberValidation(final String phoneNumber) {
        return NINE_DIGITS_FIRST_NOT_ZERO_PATTERN.matcher(phoneNumber).matches();
    }
}
