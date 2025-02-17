package ru.practicum.basic.service.event;

import org.springframework.lang.Nullable;
import ru.practicum.basic.dto.event.EventFullDto;
import ru.practicum.basic.dto.event.UpdateEventAdminRequest;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface AdminEventService {
    Collection<EventFullDto> searchEvents(@Nullable List<Long> userIds,
                                          @Nullable List<String> states,
                                          @Nullable List<Long> categories,
                                          @Nullable LocalDateTime rangeStart,
                                          @Nullable LocalDateTime rangeEnd,
                                          int from, int size);

    EventFullDto update(Long id, UpdateEventAdminRequest updateEventAdminRequest);
}
