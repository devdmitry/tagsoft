package com.rohov.tagsoft.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class UserCredential {

    @NotEmpty(message = "Missing required parameter: 'email'.")
    String email;

    @NotEmpty(message = "Missing required parameter: 'password'.")
    String password;
}
