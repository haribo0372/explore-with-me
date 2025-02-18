package ru.practicum.basic.exception.models;

import org.springframework.http.HttpStatus;

public class CommentException extends BaseCustomException {
    public CommentException(String message) {
        super(message);
    }

    public CommentException(String message, String reason) {
        super(message, reason);
    }

    public CommentException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public CommentException(String message, String reason, HttpStatus httpStatus) {
        super(message, reason, httpStatus);
    }
}
