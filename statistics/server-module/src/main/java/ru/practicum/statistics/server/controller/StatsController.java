package ru.practicum.statistics.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.statistics.dto.GetStatsParams;
import ru.practicum.statistics.models.ViewStats;
import ru.practicum.statistics.services.StatsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @GetMapping
    public Collection<ViewStats> getStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(required = false, defaultValue = "false") Boolean unique) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime startDateTime = LocalDateTime.parse(start.replace("%20", " "), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end.replace("%20", " "), formatter);

        if (startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("'start' должен быть <= 'end'");
        }

        return statsService.getStats(new GetStatsParams(startDateTime, endDateTime, uris, unique));
    }
}