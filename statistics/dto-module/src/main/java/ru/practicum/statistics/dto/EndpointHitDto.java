package ru.practicum.statistics.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EndpointHitDto {
    @NotBlank(message = "app должен быть указан")
    private String app;

    @NotBlank(message = "URI должен быть указан")
    private String uri;

    @NotBlank(message = "ip должен быть указан")
    private String ip;

    @NotNull(message = "Timestamp не может быть null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
