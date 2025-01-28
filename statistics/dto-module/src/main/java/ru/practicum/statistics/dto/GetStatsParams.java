package ru.practicum.statistics.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetStatsParams {
    private LocalDateTime start;
    private LocalDateTime end;
    private List<String> uris;
    private Boolean unique;

    public GetStatsParams(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        this.start = start;
        this.end = end;
        this.uris = uris == null ? List.of() : uris;
        this.unique = unique;
    }
}
