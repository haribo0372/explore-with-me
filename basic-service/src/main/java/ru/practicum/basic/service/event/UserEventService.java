package ru.practicum.basic.service.event;

import ru.practicum.basic.dto.event.EventFullDto;
import ru.practicum.basic.dto.event.EventShortDto;
import ru.practicum.basic.dto.event.NewEventDto;
import ru.practicum.basic.dto.event.UpdateEventUserRequest;
import ru.practicum.basic.dto.participation.EventRequestStatusUpdateRequest;
import ru.practicum.basic.dto.participation.EventRequestStatusUpdateResult;
import ru.practicum.basic.dto.participation.ParticipationRequestDto;

import java.util.Collection;

public interface UserEventService {
    Collection<EventShortDto> getAll(Long userId, int from, int size);

    EventFullDto create(Long userId, NewEventDto newEventDto);

    EventFullDto getById(Long userId, Long eventId);

    EventFullDto update(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

    Collection<ParticipationRequestDto> getRequestInfo(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequests(Long userId,
                                                  Long eventId,
                                                  EventRequestStatusUpdateRequest updateRequest);

}
