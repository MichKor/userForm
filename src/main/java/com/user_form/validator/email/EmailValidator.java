package com.user_form.validator.email;

import exception.EmailValidationException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Optional;

public class EmailValidator {

    public boolean validate(String emailAddress) {
        if (!checkEmail(emailAddress)) {
            return false;
        }
        return validateEmail(emailAddress);
    }

    private boolean checkEmail(String email) {
        if (!Optional.ofNullable(email).isPresent()) {
            return false;
        }
        if (email.isEmpty()) {
            return false;
        }
        if (email.isBlank()) {
            return false;
        }
        return true;
    }

    private boolean validateEmail(String emailAddress) {
        try {
            InternetAddress email = new InternetAddress(emailAddress);
            email.validate();
        } catch (AddressException e) {
            throw new EmailValidationException("Invalid email form (example@example.com).");
        }
        return true;
    }
}
