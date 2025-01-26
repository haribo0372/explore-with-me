package ru.practicum.statistics.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statistics.client.HitsClient;
import ru.practicum.statistics.dto.EndpointHitDto;

@RestController
@RequestMapping("/hit")
@RequiredArgsConstructor
public class HitController {
    private final HitsClient client;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> saveEndpoint(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        return client.postSaveEndpointHit(endpointHitDto);
    }
}
