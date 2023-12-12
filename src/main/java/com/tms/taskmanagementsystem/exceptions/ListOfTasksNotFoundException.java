package com.tms.taskmanagementsystem.exceptions;

public class ListOfTasksNotFoundException extends RuntimeException {
    public ListOfTasksNotFoundException() {
        super("List of tasks is not found");
    }
}
