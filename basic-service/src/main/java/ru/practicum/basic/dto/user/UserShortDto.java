package ru.practicum.basic.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserShortDto {
    @NotNull(message = "'id' должен быть указан")
    private Long id;

    @NotBlank(message = "'name' не должен быть пустым или 'null'")
    private String name;
}
