package ru.practicum.statistics.server.service.endpoint.hit;

import ru.practicum.statistics.dto.EndpointHitDto;
import ru.practicum.statistics.dto.ResponseEndpointHitDto;
import ru.practicum.statistics.server.models.ViewStats;

import java.util.List;

public interface EndpointService {
    ResponseEndpointHitDto save(EndpointHitDto endpointHitDto);

    List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique);
}
