package ru.practicum.basic.exception.models;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseCustomException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, String reason) {
        super(message, reason);
    }

    public NotFoundException(String message, String reason, HttpStatus status) {
        super(message, reason, status);
    }

    public NotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
