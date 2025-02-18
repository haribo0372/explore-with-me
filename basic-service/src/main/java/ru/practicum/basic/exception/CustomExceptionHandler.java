package ru.practicum.basic.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ru.practicum.basic.exception.models.*;
import ru.practicum.basic.models.ApiError;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.basic.exception.util.ExceptionHandlerUtils.*;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            log.warn("Ошибка валидации поля \"{}\" : {}", fieldName, errorMessage);
            errors.add(String.format("%s : %s", fieldName, errorMessage));
        });

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Ошибка корректности полей", "");
        apiError.setErrors(errors);

        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                ex.getMessage(), "Не переданы обязательные параметры");

        loggingInfo("ExceptionHandler(MissingServletRequestParameterException.class)", apiError);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiError> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                ex.getMessage(), "Неверно указаны поля/параметры");

        loggingInfo("ExceptionHandler(HandlerMethodValidationException.class)", apiError);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
        ApiError apiError = new ApiError(ex);
        loggingInfo("ExceptionHandler(NotFoundException.class)", apiError);
        return new ResponseEntity<>(apiError, calibrateStatus(apiError, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(EventException.class)
    public ResponseEntity<ApiError> handleEventException(EventException ex) {
        ApiError apiError = new ApiError(ex);
        loggingInfo("ExceptionHandler(EventException.class)", apiError);
        return new ResponseEntity<>(apiError, calibrateStatus(apiError, calibrateStatus(apiError, HttpStatus.CONFLICT)));
    }

    @ExceptionHandler(WrongRequestParam.class)
    public ResponseEntity<ApiError> handleWrongRequestParam(WrongRequestParam ex) {
        ApiError apiError = new ApiError(ex);
        loggingInfo("ExceptionHandler(WrongRequestParam.class)", apiError);
        return new ResponseEntity<>(apiError, calibrateStatus(apiError, calibrateStatus(apiError, HttpStatus.BAD_REQUEST)));
    }

    @ExceptionHandler(UserAdminException.class)
    public ResponseEntity<ApiError> handleUserAdminException(UserAdminException ex) {
        ApiError apiError = new ApiError(ex);
        loggingInfo("ExceptionHandler(UserAdminException.class)", apiError);
        return new ResponseEntity<>(apiError, calibrateStatus(apiError, calibrateStatus(apiError, HttpStatus.CONFLICT)));
    }

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<ApiError> handleCategoryException(CategoryException ex) {
        ApiError apiError = new ApiError(ex);
        loggingInfo("ExceptionHandler(CategoryException.class)", apiError);
        return new ResponseEntity<>(apiError, calibrateStatus(apiError, calibrateStatus(apiError, HttpStatus.CONFLICT)));
    }

    @ExceptionHandler(CommentException.class)
    public ResponseEntity<ApiError> handleCommentException(CommentException ex) {
        ApiError apiError = new ApiError(ex);
        loggingInfo("ExceptionHandler(CommentException.class)", apiError);
        return new ResponseEntity<>(apiError, calibrateStatus(apiError, calibrateStatus(apiError, HttpStatus.CONFLICT)));
    }

    @ExceptionHandler(WrongStateAction.class)
    public ResponseEntity<ApiError> handleNotFoundException(WrongStateAction ex) {
        ApiError apiError = new ApiError(ex);
        loggingInfo("ExceptionHandler(WrongStateAction.class)", apiError);
        return new ResponseEntity<>(apiError, calibrateStatus(apiError, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ConflictParticipationRequestException.class)
    public ResponseEntity<ApiError> handleDuplicateParticipationRequestException(
            ConflictParticipationRequestException ex) {

        ApiError apiError = new ApiError(ex);
        loggingInfo("ExceptionHandler(DuplicateParticipationRequestException.class)", apiError);
        return new ResponseEntity<>(apiError, calibrateStatus(apiError, HttpStatus.CONFLICT));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        ApiError apiError = new ApiError(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                "Конфликт данных"
        );
        loggingInfo("ExceptionHandler(ConstraintViolationException.class)", apiError);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleThrowable(Throwable ex) {
        ApiError apiError =
                new ApiError(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Произошла непредвиденная ошибка");
        loggingErr("ExceptionHandler(Throwable.class)", apiError);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
