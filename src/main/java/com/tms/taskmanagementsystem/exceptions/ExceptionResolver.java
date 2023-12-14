package com.tms.taskmanagementsystem.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResolver {
    private static final Logger log = LoggerFactory.getLogger(ExceptionResolver.class);

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<HttpStatus> userNotFoundException() {
        log.info("UserNotFoundException exception!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NotAccessException.class)
    public ResponseEntity<HttpStatus> NotAccessException() {
        log.info("NotAccessException exception!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = TaskNotFoundException.class)
    public ResponseEntity<HttpStatus> taskNotFoundException() {
        log.info("TaskNotFoundException exception!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ListOfTasksNotFoundException.class)
    public ResponseEntity<HttpStatus> listOfTasksNotFoundException() {
        log.info("ListOfTasksNotFoundException exception!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<HttpStatus> illegalArgumentException() {
        log.info("IllegalArgumentException exception!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OptimisticLockingFailureException.class)
    public ResponseEntity<HttpStatus> optimisticLockingFailureException() {
        log.info("OptimisticLockingFailureException exception!");
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
