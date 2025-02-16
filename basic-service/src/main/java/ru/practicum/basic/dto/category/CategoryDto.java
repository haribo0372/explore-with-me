package ru.practicum.basic.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "'name' не должен быть пустым или 'null'")
    @Size(min = 1, max = 50, message = "Длина 'name' должна быть не менее 1 и не более 50")
    private String name;
}
