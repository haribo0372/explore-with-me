package ru.practicum.basic.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.basic.models.Location;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NewEventDto {
    @NotBlank(message = "'annotation' не должен быть пустым или 'null'")
    @Size(min = 20, max = 2000, message = "Длина 'annotation' должна быть не менее 20 и не более 2000")
    private String annotation;

    @NotNull(message = "'category' должен быть указан")
    private Long category;

    @NotBlank(message = "'description' не должен быть пустым или 'null'")
    @Size(min = 20, max = 7000, message = "Длина 'description' должна быть не менее 20 и не более 7000")
    private String description;

    @NotBlank(message = "'eventDate' не должен быть пустым или 'null'")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotBlank(message = "'title' не должен быть пустым или 'null'")
    @Size(min = 3, max = 120, message = "Длина 'title' должна быть не менее 3 и не более 120")
    private String title;

    @Valid
    @NotNull(message = "'location' должен быть указан")
    private Location location;

    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;

    @JsonSetter(nulls = Nulls.SKIP)
    public void setPaid(Boolean paid) {
        this.paid = paid != null && paid;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setParticipantLimit(Integer participantLimit) {
        this.participantLimit = (participantLimit == null) ? 0 : participantLimit;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setRequestModeration(Boolean requestModeration) {
        this.requestModeration = requestModeration == null || requestModeration;
    }
}
