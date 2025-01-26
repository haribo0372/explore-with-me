package ru.practicum.statistics.service.stats;

import ru.practicum.statistics.dto.GetStatsParams;
import ru.practicum.statistics.models.ViewStats;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StatsService {
    Collection<ViewStats> getStats(GetStatsParams params);
}
