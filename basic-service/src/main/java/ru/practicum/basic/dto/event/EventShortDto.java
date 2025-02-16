package ru.practicum.basic.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.basic.dto.category.CategoryDto;
import ru.practicum.basic.dto.user.UserShortDto;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "'annotation' не должен быть пустым или 'null'")
    private String annotation;

    @NotNull(message = "'category' должен быть указан")
    @Valid
    private CategoryDto category;

    @NotBlank(message = "'eventDate' не должен быть пустым или 'null'")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull(message = "'initiator' должен быть указан")
    @Valid
    private UserShortDto initiator;

    @NotNull(message = "'paid' должен быть указан")
    private Boolean paid;

    @NotBlank(message = "'title' не должен быть пустым или 'null'")
    @Size(min = 3, max = 120, message = "Длина 'title' должна быть не менее 3 и не более 120")
    private String title;

    private Long confirmedRequests;
    private Long views;
}
