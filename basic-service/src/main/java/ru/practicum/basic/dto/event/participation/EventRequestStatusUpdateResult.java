package ru.practicum.basic.dto.event.participation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateResult {
    private HashSet<ParticipationRequestDto> confirmedRequests;
    private HashSet<ParticipationRequestDto> rejectedRequests;

    public boolean addToConfirmedRequests(ParticipationRequestDto prd) {
        return confirmedRequests.add(prd);
    }

    public boolean addToRejectedRequests(ParticipationRequestDto prd) {
        return rejectedRequests.add(prd);
    }
}
