package com.tms.taskmanagementsystem.security.domain;

import lombok.Data;

@Data
public class RegistrationDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String userPassword;
}
