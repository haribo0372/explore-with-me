package ru.practicum.statistics.gateway.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.statistics.dto.EndpointHitDto;

import static java.lang.String.format;

@Service
public class HitsClient extends BaseClient {
    private static final String API_PREFIX = "/hit";

    @Autowired
    public HitsClient(@Value("${ewm-stat_service.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(format("%s%s", serverUrl, API_PREFIX)))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> postSaveEndpointHit(EndpointHitDto endpointHitDto) {
        return post("", endpointHitDto);
    }
}
