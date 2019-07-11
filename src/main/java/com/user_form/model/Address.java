package com.user_form.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Address {

    protected String city;
    protected String street;
    protected int number;
    protected String postCode;
}
