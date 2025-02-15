package ru.practicum.basic.client.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.basic.client.BaseClient;
import ru.practicum.statistics.dto.EndpointHitDto;
import ru.practicum.statistics.models.ViewStats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {
    @Autowired
    public StatsClient(@Value("${ewm-statistics.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public Collection<ViewStats> getStats(LocalDateTime start, LocalDateTime end,
                                          List<String> uris, Boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedStart = start != null ? start.format(formatter) : null;
        String formattedEnd = end != null ? end.format(formatter) : null;

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/stats")
                .queryParam("start", formattedStart)
                .queryParam("end", formattedEnd)
                .queryParam("unique", unique);

        if (uris != null && !uris.isEmpty()) {
            uris.forEach(uri -> builder.queryParam("uris", uri));
        }

        HttpEntity<Void> requestEntity = new HttpEntity<>(defaultHeaders());

        ResponseEntity<Collection<ViewStats>> response = rest.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        return response.getBody();
    }

    public ResponseEntity<Object> postHit(String uri, String ip) {
        EndpointHitDto endpointHitDto = new EndpointHitDto(
                "basic-service", uri, ip, LocalDateTime.now()
        );

        return post("/hit", endpointHitDto);
    }

    private Collection<ViewStats> requestGetStats(String path, Map<String, Object> parameters) {
        HttpEntity<String> requestEntity = new HttpEntity<>(defaultHeaders());

        ResponseEntity<Collection<ViewStats>> response = rest.exchange(
                path,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                },
                parameters);

        return response.getBody();
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}
