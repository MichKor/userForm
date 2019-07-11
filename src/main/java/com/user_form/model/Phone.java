package com.user_form.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Phone {

    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private PhoneType phoneType;
}
