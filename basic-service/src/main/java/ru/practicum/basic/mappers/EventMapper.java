package ru.practicum.basic.mappers;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.basic.dto.event.EventFullDto;
import ru.practicum.basic.dto.event.EventShortDto;
import ru.practicum.basic.dto.event.NewEventDto;
import ru.practicum.basic.entity.Event;
import ru.practicum.basic.models.Location;

import java.util.Collection;

@Slf4j
public class EventMapper {
    public static EventShortDto toEventShortDto(Event event) {
        EventShortDto dto = new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                CategoryMapper.toDto(event.getCategory()),
                event.getEventDate(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                event.getConfirmedRequests(),
                event.getViews()
        );

        log.debug("mappers.EventMapper.toEventShortDto(Event{{id={}}) -> {}", event.getId(), dto);
        return dto;
    }

    public static Collection<EventShortDto> toEventShortDto(Collection<Event> events) {
        return events.stream().map(EventMapper::toEventShortDto).toList();
    }

    public static EventFullDto toEventFullDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();
        eventFullDto.setId(event.getId());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setPaid(event.getPaid());
        eventFullDto.setCategory(CategoryMapper.toDto(event.getCategory()));
        eventFullDto.setConfirmedRequests(event.getConfirmedRequests());
        eventFullDto.setLocation(new Location(event.getLocationLat(), event.getLocationLon()));
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setCreatedOn(event.getCreatedOn());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setPublishedOn(event.getPublishedOn());
        eventFullDto.setState(event.getState());
        eventFullDto.setViews(event.getViews());
        eventFullDto.setRequestModeration(event.getRequestModeration());

        return eventFullDto;
    }

    public static Collection<EventFullDto> toEventFullDto(Collection<Event> events) {
        return events.stream().map(EventMapper::toEventFullDto).toList();
    }

    public static Event fromDto(NewEventDto dto) {
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setAnnotation(dto.getAnnotation());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
        event.setLocationLat(dto.getLocation().getLat());
        event.setLocationLon(dto.getLocation().getLon());
        event.setPaid(dto.getPaid());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setRequestModeration(dto.getRequestModeration());
        return event;
    }
}