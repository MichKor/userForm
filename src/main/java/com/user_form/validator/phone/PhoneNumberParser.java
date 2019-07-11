package com.user_form.validator.phone;

import com.user_form.model.Phone;
import com.user_form.model.PhoneType;
import com.user_form.model.User;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;

public class PhoneNumberParser {

    private static final int BEGIN_INDEX_OF_CODE = 0;
    private static final int END_INDEX_OF_CODE = 2;
    private static final List<String> CODES_LIST = Collections.unmodifiableList(Arrays.asList(
            "12", "13", "14", "15", "16", "17", "18", "22", "23", "24", "25", "29", "32", "33", "34", "41", "42", "43",
            "44", "46", "48", "52", "54", "55", "56", "58", "59", "61", "62", "63", "65", "67", "68", "71", "74", "75",
            "76", "77", "81", "82", "83", "84", "85", "86", "87", "89", "91", "94", "95"));
    private PhoneNumberValidator phoneNumberValidator;

    public PhoneNumberParser(PhoneNumberValidator phoneNumberValidator) {
        this.phoneNumberValidator = phoneNumberValidator;
    }

    public boolean parse(User user) {
        if (!checkPhoneNumber(user.getPhone().getPhoneNumber())) {
            return false;
        }
        assignPhoneType(user);
        return true;
    }

    private void assignPhoneType(User user) {
        Optional<Phone> phone = parsePhone(user.getPhone().getPhoneNumber());
        user.getPhone().setPhoneType(phone.get().getPhoneType());
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        if (!Optional.ofNullable(phoneNumber).isPresent()) {
            return false;
        }
        if (phoneNumber.isEmpty()) {
            return false;
        }
        if (phoneNumber.isBlank()) {
            return false;
        }
        return true;
    }

    public Optional<Phone> parsePhone(final String phoneNumber) {
        checkArgument(nonNull(phoneNumber), "Phone number can not be null.");

        String phoneNumberWithoutDashes = removeDashesFromPhoneNumber(phoneNumber);
        return parsePhoneNumber(phoneNumberWithoutDashes);
    }

    private Optional<Phone> parsePhoneNumber(final String phoneNumber) {
        if (!phoneNumberValidator.phoneNumberValidation(phoneNumber)) {
            return Optional.empty();
        }
        final PhoneType phoneType = classify(phoneNumber);
        final String formattedNumber = formatNumber(phoneNumber, phoneType);
        return Optional.of(new Phone(formattedNumber, phoneType));
    }

    private PhoneType classify(String phoneNumber) {
        final String areaCode = phoneNumber.substring(BEGIN_INDEX_OF_CODE, END_INDEX_OF_CODE);
        return CODES_LIST.contains(areaCode) ? PhoneType.LANDLINE : PhoneType.MOBILE;
    }

    private String removeDashesFromPhoneNumber(String phoneNumber) {
        return phoneNumber.replaceAll("-", StringUtils.EMPTY);
    }

    private String formatNumber(String phoneNumber, PhoneType phoneType) {
        final String[] phoneNumberDigits = phoneNumber.split("");
        return phoneType.equals(PhoneType.LANDLINE) ? toLandLineFormat(phoneNumberDigits) : toMobileFormat(phoneNumberDigits);
    }


    private String toLandLineFormat(String[] phoneNumberDigits) {
        return phoneNumberDigits[0] +
                phoneNumberDigits[1] +
                " " +
                phoneNumberDigits[2] +
                phoneNumberDigits[3] +
                phoneNumberDigits[4] +
                "-" +
                phoneNumberDigits[5] +
                phoneNumberDigits[6] +
                "-" +
                phoneNumberDigits[7] +
                phoneNumberDigits[8];
    }

    private String toMobileFormat(String[] phoneNumberDigits) {
        return phoneNumberDigits[0] +
                phoneNumberDigits[1] +
                phoneNumberDigits[2] +
                "-" +
                phoneNumberDigits[3] +
                phoneNumberDigits[4] +
                phoneNumberDigits[5] +
                "-" +
                phoneNumberDigits[6] +
                phoneNumberDigits[7] +
                phoneNumberDigits[8];
    }
}
