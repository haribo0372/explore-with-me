package ru.practicum.basic.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;
import ru.practicum.basic.exception.models.BaseCustomException;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {
    private final HttpStatus status;
    private final String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;

    private List<String> errors;
    private final String reason;

    public ApiError(BaseCustomException exception) {
        this.reason = exception.reason;
        this.status = exception.status;
        this.message = exception.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(Throwable exception, HttpStatus status, String reason) {
        this.status = status;
        this.message = exception.getMessage();
        this.timestamp = LocalDateTime.now();
        this.reason = reason;
    }

    public ApiError(HttpStatus status, String message, String reason) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.reason = reason;
    }
}
