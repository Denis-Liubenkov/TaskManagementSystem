package com.tms.taskmanagementsystem.exceptions;

public class NotAccessException extends RuntimeException {
    public NotAccessException() {
        super("No access");
    }
}
