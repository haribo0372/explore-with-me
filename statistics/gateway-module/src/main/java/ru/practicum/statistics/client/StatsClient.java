package ru.practicum.statistics.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.statistics.dto.GetStatsParams;
import ru.practicum.statistics.util.DateTimeFormating;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Service
public class StatsClient extends BaseClient implements DateTimeFormating {
    private static final String API_PREFIX = "/stats";

    @Autowired
    public StatsClient(@Value("${ewm-stat_service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(format("%s%s", serverUrl, API_PREFIX)))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getStats(LocalDateTime start,
                                           LocalDateTime end,
                                           List<String> uris,
                                           Boolean unique) {

        GetStatsParams requestBody = new GetStatsParams(start, end, uris, unique);
        return get("", requestBody);
    }
}
