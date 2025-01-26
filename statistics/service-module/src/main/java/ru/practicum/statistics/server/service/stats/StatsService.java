package ru.practicum.statistics.server.service.stats;

import ru.practicum.statistics.dto.GetStatsParams;
import ru.practicum.statistics.server.models.ViewStats;

import java.util.Collection;

public interface StatsService {
    Collection<ViewStats> getStats(GetStatsParams params);
}
