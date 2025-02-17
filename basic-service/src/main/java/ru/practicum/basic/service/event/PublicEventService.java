package ru.practicum.basic.service.event;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.basic.dto.event.EventFullDto;
import ru.practicum.basic.dto.event.EventShortDto;
import ru.practicum.basic.models.enums.SortPublishEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

public interface PublicEventService {
    Collection<EventShortDto> getAllPublishEvents(String text,
                                                  ArrayList<Integer> categories,
                                                  Boolean paid,
                                                  LocalDateTime rangeStart,
                                                  LocalDateTime rangeEnd,
                                                  Boolean onlyAvailable,
                                                  SortPublishEvent sort,
                                                  int from,
                                                  int size, HttpServletRequest request);

    EventFullDto getPublishEventById(Long eventId, HttpServletRequest request);
}
