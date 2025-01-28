package ru.practicum.basic.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    @NotNull(message = "'lat' должен быть указан")
    private Float lat;
    @NotNull(message = "'lon' должен быть указан")
    private Float lon;
}
