package ru.practicum.statistics.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.statistics.dto.GetStatsParams;
import ru.practicum.statistics.server.models.ViewStats;
import ru.practicum.statistics.server.service.stats.StatsService;

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
