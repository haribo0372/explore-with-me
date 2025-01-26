package ru.practicum.statistics.service.stats;

import ru.practicum.statistics.dto.GetStatsParams;
import ru.practicum.statistics.models.ViewStats;

import java.util.Collection;

public interface StatsService {
    Collection<ViewStats> getStats(GetStatsParams params);
}
