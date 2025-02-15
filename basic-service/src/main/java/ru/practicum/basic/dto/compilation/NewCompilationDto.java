package ru.practicum.basic.dto.compilation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    @JsonProperty("events")
    @JsonSetter(nulls = Nulls.SKIP)
    private HashSet<Long> eventIds = new HashSet<>();

    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean pinned = false;

    @NotBlank(message = "'title' не должен быть пустым или 'null'")
    @Size(min = 1, max = 50, message = "Длина 'title' должна быть не менее 1 и не более 50")
    private String title;
}
