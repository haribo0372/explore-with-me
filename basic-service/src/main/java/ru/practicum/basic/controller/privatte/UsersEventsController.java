package ru.practicum.basic.controller.privatte;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.event.EventFullDto;
import ru.practicum.basic.dto.event.EventShortDto;
import ru.practicum.basic.dto.event.NewEventDto;
import ru.practicum.basic.dto.event.UpdateEventUserRequest;
import ru.practicum.basic.dto.event.participation.EventRequestStatusUpdateRequest;
import ru.practicum.basic.dto.event.participation.EventRequestStatusUpdateResult;
import ru.practicum.basic.dto.event.participation.ParticipationRequestDto;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/events")
public class UsersEventsController {
    @GetMapping
    public Collection<EventShortDto> getAddedEventsByUserId(@PathVariable Long userId,
                                                            @RequestParam(required = false, defaultValue = "0") int from,
                                                            @RequestParam(required = false, defaultValue = "10") int size) {
        return null;
    }

    @PostMapping("/{userId}/requests")
    public EventFullDto addEventByUserId(@PathVariable Long userId,
                                         @RequestBody @Valid NewEventDto newEventDto) {
        return null;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable Long userId,
                                     @PathVariable Long eventId) {
        return null;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventById(@PathVariable Long userId,
                                        @PathVariable Long eventId,
                                        @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

    @GetMapping("/{eventId}/requests")
    public ParticipationRequestDto getRequestsInfo(@PathVariable Long userId,
                                                   @PathVariable Long eventId) {
        return null;
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequests(@PathVariable Long userId,
                                                         @PathVariable Long eventId,
                                                         @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return null;
    }

}
