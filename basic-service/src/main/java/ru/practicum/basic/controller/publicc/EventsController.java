package ru.practicum.basic.controller.publicc;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.event.EventFullDto;
import ru.practicum.basic.dto.event.EventShortDto;
import ru.practicum.basic.exception.models.WrongRequestParam;
import ru.practicum.basic.models.enums.SortPublishEvent;
import ru.practicum.basic.service.event.PublicEventService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventsController {
    private final PublicEventService eventService;

    @GetMapping
    public Collection<EventShortDto> getAllEvent(@RequestParam(required = false) String text,
                                                 @RequestParam(required = false) ArrayList<Integer> categories,
                                                 @RequestParam(required = false) Boolean paid,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                 @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
                                                 @RequestParam(required = false) SortPublishEvent sort,
                                                 @RequestParam(required = false, defaultValue = "0") int from,
                                                 @RequestParam(required = false, defaultValue = "10") int size,
                                                 HttpServletRequest request) {
        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new WrongRequestParam("'rangeStart' должен быть <= 'rangeEnd'", HttpStatus.BAD_REQUEST);
        }

        return eventService.getAllPublishEvents(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, request
        );
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable Long eventId, HttpServletRequest request) {
        return eventService.getPublishEventById(eventId, request);
    }

}
