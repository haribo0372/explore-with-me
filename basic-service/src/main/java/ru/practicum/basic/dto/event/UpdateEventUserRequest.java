package ru.practicum.basic.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.basic.models.Location;
import ru.practicum.basic.models.enums.StateAction;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest {
    @Size(min = 20, max = 2000, message = "Длина 'annotation' должна быть не менее 20 и не более 2000")
    private String annotation;

    @JsonProperty("category")
    private Long categoryId;

    @Size(min = 20, max = 7000, message = "Длина 'description' должна быть не менее 20 и не более 7000")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @Size(min = 3, max = 120, message = "Длина 'title' должна быть не менее 3 и не более 120")
    private String title;

    private Location location;
    private Boolean paid;

    @Min(value = 0, message = "'participantLimit' должен быть > 0")
    private Integer participantLimit;

    private Boolean requestModeration;
    private StateAction stateAction;

    public void setStateAction(String stateAction) {
        this.stateAction = StateAction.fromString(stateAction, false);
    }
}
