package ru.practicum.basic.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.basic.models.Location;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {
    @NotBlank(message = "'annotation' не должен быть пустым или 'null'")
    @Size(min = 20, max = 2000, message = "Длина 'annotation' должна быть не менее 20 и не более 2000")
    private String annotation;

    @NotNull(message = "'category' должен быть указан")
    private Long category;

    @NotBlank(message = "'description' не должен быть пустым или 'null'")
    @Size(min = 20, max = 7000, message = "Длина 'description' должна быть не менее 20 и не более 7000")
    private String description;

    @NotNull(message = "'eventDate' не должен 'null'")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime eventDate;

    @NotBlank(message = "'title' не должен быть пустым или 'null'")
    @Size(min = 3, max = 120, message = "Длина 'title' должна быть не менее 3 и не более 120")
    private String title;

    @Valid
    @NotNull(message = "'location' должен быть указан")
    private Location location;

    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean paid = false;

    @JsonSetter(nulls = Nulls.SKIP)
    @Min(value = 0, message = "'participantLimit' должен быть > 0")
    private Integer participantLimit = 0;

    @JsonSetter(nulls = Nulls.SKIP)
    private Boolean requestModeration = true;
}
