package ru.practicum.statistics.server.service.endpoint.hit.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statistics.dto.EndpointHitDto;
import ru.practicum.statistics.dto.ResponseEndpointHitDto;
import ru.practicum.statistics.server.entity.EndpointHit;
import ru.practicum.statistics.server.mapper.EndpointMapper;
import ru.practicum.statistics.server.repository.EndpointHitRepository;
import ru.practicum.statistics.services.EndpointService;

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
}
