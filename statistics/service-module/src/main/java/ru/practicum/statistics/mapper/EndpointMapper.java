package ru.practicum.statistics.mapper;

import ru.practicum.statistics.util.DateTimeFormating;
import ru.practicum.statistics.dto.EndpointHitDto;
import ru.practicum.statistics.dto.ResponseEndpointHitDto;
import ru.practicum.statistics.entity.EndpointHit;

public class EndpointMapper implements DateTimeFormating {

    public static EndpointHit fromDto(EndpointHitDto dto) {
        return new EndpointHit(null, dto.getApp(), dto.getUri(), dto.getIp(), dto.getTimestamp());
    }

    public static ResponseEndpointHitDto toDto(EndpointHit endpointHit) {
        return new ResponseEndpointHitDto(
                endpointHit.getId(),
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                endpointHit.getTimestamp().format(formatter)
        );
    }
}
