package com.user_form.validator.user;

import com.user_form.model.User;
import com.user_form.validator.email.EmailValidator;
import com.user_form.validator.fullName.FullNameValidator;
import com.user_form.validator.phone.PhoneNumberParser;
import com.user_form.validator.phone.PhoneNumberValidator;
import exception.UserValidationException;

public class UserValidator {

    private EmailValidator emailValidator = new EmailValidator();
    private PhoneNumberValidator phoneNumberValidator = new PhoneNumberValidator();
    private PhoneNumberParser phoneNumberParser = new PhoneNumberParser(phoneNumberValidator);
    private FullNameValidator fullNameValidator = new FullNameValidator();

    public void validate(User user) {
        fullNameValidator.validateFullName(user.getFirstName(), user.getLastName());
        if (!emailValidator.validate(user.getEmail())) {
            if (!phoneNumberParser.parse(user)) {
                throw new UserValidationException("One of the fields: Telephone number or Email should be completed.");
            }
        } else if (emailValidator.validate(user.getEmail())) {
            phoneNumberParser.parse(user);
        }
    }
}
