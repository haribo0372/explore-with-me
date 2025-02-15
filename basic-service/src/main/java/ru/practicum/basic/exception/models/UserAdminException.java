package ru.practicum.basic.exception.models;

import org.springframework.http.HttpStatus;

public class UserAdminException extends BaseCustomException {
    public UserAdminException(String message) {
        super(message);
    }

    public UserAdminException(String message, String reason) {
        super(message, reason);
    }

    public UserAdminException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public UserAdminException(String message, String reason, HttpStatus httpStatus) {
        super(message, reason, httpStatus);
    }
}