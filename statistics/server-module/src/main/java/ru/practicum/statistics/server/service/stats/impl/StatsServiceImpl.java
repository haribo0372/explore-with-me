package ru.practicum.statistics.server.service.stats.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statistics.dto.GetStatsParams;
import ru.practicum.statistics.models.ViewStats;
import ru.practicum.statistics.server.repository.EndpointHitRepository;
import ru.practicum.statistics.services.StatsService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final EndpointHitRepository endpointHitRepository;

    @Override
    public Collection<ViewStats> getStats(GetStatsParams params) {
        LocalDateTime start = params.getStart();
        LocalDateTime end = params.getEnd();

        List<ViewStats> viewStats = params.getUnique() ?
                endpointHitRepository.getStatsWithUniqueIp(start, end, params.getUris().isEmpty() ? null : params.getUris())
                : endpointHitRepository.getStatsWithoutUniqueIp(start, end, params.getUris().isEmpty() ? null : params.getUris());

        log.info("StatsServiceImpl::getStats({}, {}, {}, {}) -> {}",
                start, end, params.getUnique(), params.getUnique(), viewStats);
        viewStats.sort((o1, o2) -> Long.compare(o2.getHits(), o1.getHits()));
        return viewStats;
    }
}
