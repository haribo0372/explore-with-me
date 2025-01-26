package ru.practicum.statistics.service.endpoint.hit.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statistics.dto.EndpointHitDto;
import ru.practicum.statistics.dto.ResponseEndpointHitDto;
import ru.practicum.statistics.entity.EndpointHit;
import ru.practicum.statistics.mapper.EndpointMapper;
import ru.practicum.statistics.models.ViewStats;
import ru.practicum.statistics.repository.EndpointHitRepository;
import ru.practicum.statistics.service.endpoint.hit.EndpointService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EndpointServiceImpl implements EndpointService {
    private final EndpointHitRepository endpointHitRepository;

    @Override
    public ResponseEndpointHitDto save(EndpointHitDto endpointHitDto) {
        EndpointHit endpointHit = EndpointMapper.fromDto(endpointHitDto);
        EndpointHit saved = endpointHitRepository.save(endpointHit);
        log.info("EndpointHit {} saved", saved);
        return EndpointMapper.toDto(saved);
    }

    @Override
    public List<ViewStats> getStats(String start, String end, List<String> uris, Boolean unique) {
        return List.of();
    }
}
