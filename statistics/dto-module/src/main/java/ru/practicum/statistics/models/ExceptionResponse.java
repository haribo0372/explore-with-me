package ru.practicum.statistics.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    @JsonProperty("status")
    private int statusCode;
    private HttpStatus error;
    private String path;
    private String method;
    private Object message;

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "statusCode=" + statusCode +
                ", error=" + error +
                ", path='" + path + '\'' +
                ", method='" + method + '\'' +
                ", message=" + message +
                '}';
    }
}
