package com.user_form.validator.phone;

import com.user_form.model.Phone;
import com.user_form.model.PhoneType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneNumberParserTest {

    private final PhoneNumberValidator phoneNumberValidator = mock(PhoneNumberValidator.class);
    private PhoneNumberParser phoneNumberParser;

    @BeforeEach
    void setUp() {
        phoneNumberParser = new PhoneNumberParser(phoneNumberValidator);
    }

    @AfterEach
    void tearDown() {
        reset(phoneNumberValidator);
    }

    private static Stream<Arguments> phoneNumbersWithDashes() {
        return Stream.of(
                Arguments.of("500-500-500", "500500500"),
                Arguments.of("22-5-0-0-5-0-50", "225005050"),
                Arguments.of("2-2-5-0-0-5-0-5-0", "225005050"),
                Arguments.of("-2-2-5-0-0-5-0-5-0-", "225005050")
        );
    }

    @ParameterizedTest
    @MethodSource("phoneNumbersWithDashes")
    void shouldCallPhoneValidatorAfterRemovingDashes(String phoneNumber, String numberToValidate) {
        //given
        doReturn(Boolean.TRUE).when(phoneNumberValidator).phoneNumberValidation(anyString());
        //when
        phoneNumberParser.parsePhone(phoneNumber);
        //then
        verify(phoneNumberValidator).phoneNumberValidation(numberToValidate);
    }

    @Test
    void shouldReturnEmptyOptionalForFalseFromPhoneValidator() {
        //given
        final String correctMobileNumberWithoutDashes = "500500500";
        doReturn(Boolean.FALSE).when(phoneNumberValidator).phoneNumberValidation(anyString());
        //when
        final Optional<Phone> phoneOptional = phoneNumberParser.parsePhone(correctMobileNumberWithoutDashes);
        //then
        assertThat(phoneOptional).isEmpty();
    }

    private static Stream<Arguments> phoneNumbers() {
        return Stream.of(
                Arguments.of("500500500", PhoneType.MOBILE, "500-500-500"),
                Arguments.of("50-050-05-00", PhoneType.MOBILE, "500-500-500"),
                Arguments.of("52-050-05-00", PhoneType.LANDLINE, "52 050-05-00"),
                Arguments.of("81-050-05-00", PhoneType.LANDLINE, "81 050-05-00"),
                Arguments.of("225005050", PhoneType.LANDLINE, "22 500-50-50")
        );
    }

    @ParameterizedTest
    @MethodSource("phoneNumbers")
    void shouldReturnCorrectPhoneData(String phoneNumber, PhoneType phoneType, String formattedPhoneNumber) {
        //given
        doReturn(Boolean.TRUE).when(phoneNumberValidator).phoneNumberValidation(anyString());
        //when
        final Optional<Phone> phoneOptional = phoneNumberParser.parsePhone(phoneNumber);
        //then
        assertThat(phoneOptional).map(Phone::getPhoneType).hasValue(phoneType);
        assertThat(phoneOptional).map(Phone::getPhoneNumber).hasValue(formattedPhoneNumber);
    }

}
