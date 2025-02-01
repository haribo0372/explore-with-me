package ru.practicum.basic.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.event.EventFullDto;
import ru.practicum.basic.dto.event.UpdateEventAdminRequest;
import ru.practicum.basic.service.event.AdminEventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventsController {

    private final AdminEventService eventService;

    @GetMapping
    public List<EventFullDto> getEvents(@RequestParam(required = false) List<Long> users,
                                        @RequestParam(required = false) List<String> states,
                                        @RequestParam(required = false) List<Long> categories,
                                        @RequestParam(required = false) LocalDateTime rangeStart,
                                        @RequestParam(required = false) LocalDateTime rangeEnd,
                                        @RequestParam(required = false, defaultValue = "0") int from,
                                        @RequestParam(required = false, defaultValue = "10") int size) {

        return eventService.searchEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto patchEvent(@PathVariable Long eventId,
                                   @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        return eventService.update(eventId, updateEventAdminRequest);
    }
}
