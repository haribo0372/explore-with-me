package ru.practicum.basic.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentDto {
    @NotNull(message = "'id' должен быть указан")
    private Long id;

    @NotBlank(message = "'text' не должен быть пустым или null")
    @Size(max = 2000, message = "Длина 'text' не должна превышать 2000 символов")
    private String text;
}
