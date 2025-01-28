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
import lombok.NoArgsConstructor;
import ru.practicum.basic.dto.category.CategoryDto;
import ru.practicum.basic.dto.user.UserShortDto;
import ru.practicum.basic.models.Location;
import ru.practicum.basic.models.enums.EventsLifeCycle;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private Long id;

    @NotBlank(message = "'annotation' не должен быть пустым или 'null'")
    private String annotation;

    @NotNull(message = "'category' должен быть указан")
    @Valid
    private CategoryDto category;

    private Long confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @NotBlank(message = "'description' не должен быть пустым или 'null'")
    @Size(min = 20, max = 7000, message = "Длина 'description' должна быть не менее 20 и не более 7000")
    private String description;

    @NotBlank(message = "'eventDate' не должен быть пустым или 'null'")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull(message = "'initiator' должен быть указан")
    @Valid
    private UserShortDto initiator;

    @NotNull(message = "'location' должен быть указан")
    @Valid
    private Location location;

    @NotNull(message = "'paid' должен быть указан")
    private Boolean paid;

    private Integer participantLimit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private EventsLifeCycle state;

    @NotBlank(message = "'title' не должен быть пустым или 'null'")
    @Size(min = 3, max = 120, message = "Длина 'title' должна быть не менее 3 и не более 120")
    private String title;

    private Long views;

    @JsonSetter(nulls = Nulls.SKIP)
    public void setParticipantLimit(Integer participantLimit) {
        this.participantLimit = (participantLimit == null) ? 0 : participantLimit;
    }

    @JsonSetter(nulls = Nulls.SKIP)
    public void setRequestModeration(Boolean requestModeration) {
        this.requestModeration = requestModeration == null || requestModeration;
    }

    public void setState(String state) {
        this.state = EventsLifeCycle.fromString(state);
    }
}
