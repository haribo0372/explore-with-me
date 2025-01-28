package ru.practicum.basic.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewUserRequest {
    @NotBlank(message = "'name' не должен быть пустым или 'null'")
    @Size(min = 2, max = 250, message = "Длина 'name' должна быть не менее 2 и не более 250")
    private String name;

    @NotBlank(message = "'email' не должен быть пустым или 'null'")
    @Size(min = 6, max = 254, message = "Длина 'name' должна быть не менее 2 и не более 250")
    private String email;
}
