package ru.practicum.basic.dto.compilation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.basic.dto.event.EventShortDto;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    @NotNull(message = "'id' должен быть указан")
    private Long id;

    @NotBlank(message = "'title' не должен быть пустым или 'null'")
    private String title;

    @NotNull(message = "'pinned' должен быть указан")
    private Boolean pinned;

    private Set<EventShortDto> events;
}
