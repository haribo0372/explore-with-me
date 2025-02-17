package ru.practicum.basic.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
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
public class UpdateEventAdminRequest {
    @Size(min = 20, max = 2000, message = "Длина 'annotation' должна быть не менее 20 и не более 2000")
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000, message = "Длина 'description' должна быть не менее 20 и не более 7000")
    private String description;

    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @Size(min = 3, max = 120, message = "Длина 'title' должна быть не менее 3 и не более 120")
    private String title;

    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;

    public void setStateAction(String stateAction) {
        this.stateAction = StateAction.fromString(stateAction, true);
    }
}
