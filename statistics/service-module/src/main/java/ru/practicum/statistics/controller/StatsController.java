package ru.practicum.statistics.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statistics.dto.GetStatsParams;
import ru.practicum.statistics.models.ViewStats;
import ru.practicum.statistics.service.stats.StatsService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @GetMapping
    public Collection<ViewStats> getStats(@RequestBody GetStatsParams params) {

        return statsService.getStats(params);
    }
}
