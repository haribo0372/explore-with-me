package ru.practicum.basic.exception.models;

import org.springframework.http.HttpStatus;

public class CategoryException extends BaseCustomException {
    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, String reason) {
        super(message, reason);
    }

    public CategoryException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public CategoryException(String message, String reason, HttpStatus httpStatus) {
        super(message, reason, httpStatus);
    }
}