package ru.practicum.basic.dto.participation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.basic.models.enums.ParticipationRequestStatus;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    @JsonProperty("id")
    @EqualsAndHashCode.Include
    private Long participationRequestId;

    @JsonProperty("event")
    private Long eventId;

    @JsonProperty("requester")
    private Long requesterId;

    private LocalDateTime created;

    private ParticipationRequestStatus status;

    public void setStatus(String status) {
        this.status = ParticipationRequestStatus.fromString(status);
    }
}
