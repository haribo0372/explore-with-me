package ru.practicum.basic.exception.models;

import org.springframework.http.HttpStatus;

public class EventException extends BaseCustomException {
    public EventException(String message) {
        super(message);
    }

    public EventException(String message, String reason) {
        super(message, reason);
    }

    public EventException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public EventException(String message, String reason, HttpStatus httpStatus) {
        super(message, reason, httpStatus);
    }
}
