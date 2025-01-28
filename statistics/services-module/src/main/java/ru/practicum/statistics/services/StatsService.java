package ru.practicum.statistics.services;

import ru.practicum.statistics.dto.GetStatsParams;
import ru.practicum.statistics.models.ViewStats;

import java.util.Collection;

public interface StatsService {
    Collection<ViewStats> getStats(GetStatsParams params);
}
