package com.user_form.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    protected Address address;
    private String email;
    private Phone phone;
    @Size(min = 10, max = 500)
    private String description;
}
