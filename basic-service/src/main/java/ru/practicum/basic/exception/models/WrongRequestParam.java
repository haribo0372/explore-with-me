package ru.practicum.basic.exception.models;

import org.springframework.http.HttpStatus;

public class WrongRequestParam extends BaseCustomException {
    public WrongRequestParam(String message) {
        super(message);
    }

    public WrongRequestParam(String message, String reason) {
        super(message, reason);
    }

    public WrongRequestParam(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public WrongRequestParam(String message, String reason, HttpStatus httpStatus) {
        super(message, reason, httpStatus);
    }
}
