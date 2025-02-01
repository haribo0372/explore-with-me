package ru.practicum.basic.service.participation;

import ru.practicum.basic.dto.participation.ParticipationRequestDto;

import java.util.Collection;

public interface UserParticipationRequestService {
    Collection<ParticipationRequestDto> getAllRequestsOfUser(Long userId);

    ParticipationRequestDto create(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long eventId);

}
