package ru.practicum.basic.exception.models;

import org.springframework.http.HttpStatus;

public class ConflictParticipationRequestException extends BaseCustomException {
    public ConflictParticipationRequestException(String message) {
        super(message);
    }

    public ConflictParticipationRequestException(String message, String reason) {
        super(message, reason);
    }

    public ConflictParticipationRequestException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    public ConflictParticipationRequestException(String message, String reason, HttpStatus httpStatus) {
        super(message, reason, httpStatus);
    }
}