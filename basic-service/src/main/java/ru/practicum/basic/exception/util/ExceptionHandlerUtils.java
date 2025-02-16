package ru.practicum.basic.exception.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import ru.practicum.basic.models.ApiError;

@Slf4j
public class ExceptionHandlerUtils {

    public static HttpStatus calibrateStatus(ApiError apiError, HttpStatus status) {
        HttpStatus current = apiError.getStatus();
        return current == null ? status : current;
    }

    public static void loggingInfo(String preMessage, ApiError apiError) {
        log.info("{} -> {}", preMessage, apiError);
    }

    public static void loggingErr(String preMessage, ApiError apiError) {
        log.error("{} -> {}", preMessage, apiError);
    }

}