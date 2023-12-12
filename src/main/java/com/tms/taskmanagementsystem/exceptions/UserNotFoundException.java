package com.tms.taskmanagementsystem.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User is not found");
    }
}
