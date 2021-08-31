package com.bluesoft.ui.model;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateUserRequestModel {

    @NotNull(message="First name cannot be null!")
    @Size(min = 2, message = "First name must not be less then two characters")
    private String firstName;

    @NotNull(message="Last name cannot be null!")
    @Size(min = 2, message = "Last name must not be less then two characters")
    private String lastName;

    @NotNull(message="password cannot be null!")
    @Size(min = 8,max=16, message = "password must not be less then eight characters")
    private String password;

    @NotNull(message="email cannot be null!")
    @Email(message = "not email format")
    private String email;
}
