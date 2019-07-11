package com.user_form.validator.fullName;

import exception.UserValidationException;

import java.util.Optional;

public class FullNameValidator {

    public boolean validateFullName(String firstName, String lastName) {
        if (!(checkFirstName(firstName) && checkLastName(lastName))) {
            throw new UserValidationException("The fields: Name and Surname should by completed.");
        }
        return true;
    }

    private boolean checkFirstName(String firstName) {
        if (!Optional.ofNullable(firstName).isPresent()) {
            return false;
        }
        if (firstName.isEmpty()) {
            return false;
        }
        if (firstName.isBlank()) {
            return false;
        }
        return true;
    }

    private boolean checkLastName(String lastName) {
        if (!Optional.ofNullable(lastName).isPresent()) {
            return false;
        }
        if (lastName.isEmpty()) {
            return false;
        }
        if (lastName.isBlank()) {
            return false;
        }
        return true;
    }
}
