package com.tms.taskmanagementsystem.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("Task is not found");
    }
}
