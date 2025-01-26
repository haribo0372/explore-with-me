package ru.practicum.statistics.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.statistics.models.ExceptionResponse;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                                        HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ExceptionResponse exResp = new ExceptionResponse(
                400,
                HttpStatus.BAD_REQUEST,
                request.getRequestURI(),
                request.getMethod(),
                errors
        );

        log.info("ExceptionHandler(MethodArgumentNotValidException.class):://\t{}", exResp);
        return new ResponseEntity<>(exResp, exResp.getError());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex,
                                                                            HttpServletRequest request) {
        ExceptionResponse exResp = new ExceptionResponse(
                400,
                HttpStatus.BAD_REQUEST,
                request.getRequestURI(),
                request.getMethod(),
                ex.getMessage()
        );

        log.info("ExceptionHandler(IllegalArgumentException.class):://\t{}", exResp);
        return new ResponseEntity<>(exResp, exResp.getError());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionResponse> handleThrowable(Throwable ex, HttpServletRequest request){
        ExceptionResponse exResp = new ExceptionResponse(
                500,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getRequestURI(),
                request.getMethod(),
                ex.getMessage()
        );

        log.info("ExceptionHandler(Throwable.class):://\t{}", exResp);
        ex.printStackTrace();
        return new ResponseEntity<>(exResp, exResp.getError());
    }
}