package com.tms.taskmanagementsystem.exceptions;

public class ExecutorNotFoundException extends RuntimeException {
    public ExecutorNotFoundException() {
        super("Executor is not found");
    }
}
