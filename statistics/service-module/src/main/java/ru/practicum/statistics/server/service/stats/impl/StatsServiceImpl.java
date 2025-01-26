package ru.practicum.statistics.service.stats.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statistics.dto.GetStatsParams;
import ru.practicum.statistics.models.ViewStats;
import ru.practicum.statistics.repository.EndpointHitRepository;
import ru.practicum.statistics.service.stats.StatsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final EndpointHitRepository endpointHitRepository;

    @Override
    public Collection<ViewStats> getStats(GetStatsParams params) {

        List<ViewStats> viewStats = new ArrayList<>();

        LocalDateTime start = params.getStart();
        LocalDateTime end = params.getEnd();

        if (params.getUnique())
            params.getUris().forEach(uri ->
                    viewStats.addAll(endpointHitRepository.getStatsWithUniqueIp(start, end, uri)));
        else
            params.getUris().stream().forEach(uri ->
                    viewStats.addAll(endpointHitRepository.getStatsWithoutUniqueIp(start, end, uri)));

        log.info("StatsServiceImpl::getStats({}, {}, {}, {}) -> {}",
                start, end, params.getUnique(), params.getUnique(), viewStats);

        return viewStats;
    }
}
