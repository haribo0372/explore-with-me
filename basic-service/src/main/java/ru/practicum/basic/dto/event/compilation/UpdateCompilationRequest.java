package ru.practicum.basic.dto.event.compilation;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompilationRequest {
    @JsonProperty("events")
    private HashSet<Integer> eventIds;

    private Boolean pinned;

    @Size(min = 1, max = 50, message = "Длина 'title' должна быть не менее 1 и не более 50")
    private String title;
}
