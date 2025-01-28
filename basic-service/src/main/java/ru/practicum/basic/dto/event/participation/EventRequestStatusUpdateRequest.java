package ru.practicum.basic.dto.event.participation;

import lombok.Data;
import ru.practicum.basic.models.enums.EventRequestStatus;

import java.util.HashSet;

@Data
public class EventRequestStatusUpdateRequest {
    private HashSet<Long> requestIds;
    private EventRequestStatus status;

    public void setStatus(String status) {
        this.status = EventRequestStatus.fromString(status);
    }
}
