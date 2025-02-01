package ru.practicum.basic.controller.privatte;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.event.EventFullDto;
import ru.practicum.basic.dto.event.EventShortDto;
import ru.practicum.basic.dto.event.NewEventDto;
import ru.practicum.basic.dto.event.UpdateEventUserRequest;
import ru.practicum.basic.dto.participation.EventRequestStatusUpdateRequest;
import ru.practicum.basic.dto.participation.EventRequestStatusUpdateResult;
import ru.practicum.basic.dto.participation.ParticipationRequestDto;
import ru.practicum.basic.service.event.UserEventService;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class UsersEventsController {

    private final UserEventService eventService;

    @GetMapping
    public Collection<EventShortDto> getAddedEventsByUserId(@PathVariable Long userId,
                                                            @RequestParam(required = false, defaultValue = "0") int from,
                                                            @RequestParam(required = false, defaultValue = "10") int size) {
        return eventService.getAll(userId, from, size);
    }

    @PostMapping("/{userId}/requests")
    public EventFullDto addEventWithUserId(@PathVariable Long userId,
                                         @RequestBody @Valid NewEventDto newEventDto) {
        return eventService.create(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable Long userId,
                                     @PathVariable Long eventId) {
        return eventService.getById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventById(@PathVariable Long userId,
                                        @PathVariable Long eventId,
                                        @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) {
        return eventService.update(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/{eventId}/requests")
    public ParticipationRequestDto getRequestInfo(@PathVariable Long userId,
                                                   @PathVariable Long eventId) {
        return eventService.getRequestInfo(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequests(@PathVariable Long userId,
                                                         @PathVariable Long eventId,
                                                         @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return eventService.updateRequests(userId, eventId, eventRequestStatusUpdateRequest);
    }

}
