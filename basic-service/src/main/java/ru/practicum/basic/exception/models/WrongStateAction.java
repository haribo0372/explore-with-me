package ru.practicum.basic.exception.models;

import org.springframework.http.HttpStatus;

public class WrongStateAction extends BaseCustomException {
    public WrongStateAction(String message) {
        super(message);
    }

    public WrongStateAction(String message, String reason) {
        super(message, reason);
    }

    public WrongStateAction(String message, String reason, HttpStatus status) {
        super(message, reason, status);
    }

    public WrongStateAction(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
