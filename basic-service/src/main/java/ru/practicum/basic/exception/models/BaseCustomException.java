package ru.practicum.basic.exception.models;

import org.springframework.http.HttpStatus;

public abstract class BaseCustomException extends RuntimeException {
    public final String reason;
    public HttpStatus status;

    public BaseCustomException(String message) {
        super(message);
        this.reason = message;
    }

    public BaseCustomException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public BaseCustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.reason = message;
        this.status = httpStatus;
    }


    public BaseCustomException(String message, String reason, HttpStatus httpStatus) {
        super(message);
        this.reason = reason;
        this.status = httpStatus;
    }
}
