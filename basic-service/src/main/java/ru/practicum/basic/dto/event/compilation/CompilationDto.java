package ru.practicum.basic.dto.event.compilation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.basic.dto.event.EventShortDto;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    @NotNull(message = "'id' должен быть указан")
    private Long id;

    @NotNull(message = "'pinned' должен быть указан")
    private Boolean pinned;

    @NotBlank(message = "'title' не должен быть пустым или 'null'")
    private String title;

    private HashSet<EventShortDto> events;
}
