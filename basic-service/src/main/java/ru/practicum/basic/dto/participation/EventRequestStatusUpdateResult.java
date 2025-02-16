package ru.practicum.basic.dto.participation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateResult {
    private Collection<ParticipationRequestDto> confirmedRequests;
    private Collection<ParticipationRequestDto> rejectedRequests;

    public boolean addToConfirmedRequests(ParticipationRequestDto prd) {
        return confirmedRequests.add(prd);
    }

    public boolean addToRejectedRequests(ParticipationRequestDto prd) {
        return rejectedRequests.add(prd);
    }
}
