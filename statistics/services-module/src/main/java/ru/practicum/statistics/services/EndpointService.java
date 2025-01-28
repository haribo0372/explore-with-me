package ru.practicum.statistics.services;

import ru.practicum.statistics.dto.EndpointHitDto;
import ru.practicum.statistics.dto.ResponseEndpointHitDto;

public interface EndpointService {
    ResponseEndpointHitDto save(EndpointHitDto endpointHitDto);
}
