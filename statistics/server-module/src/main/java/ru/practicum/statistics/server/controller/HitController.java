package ru.practicum.statistics.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statistics.dto.EndpointHitDto;
import ru.practicum.statistics.dto.ResponseEndpointHitDto;
import ru.practicum.statistics.services.EndpointService;

@RestController
@RequestMapping("/hit")
@RequiredArgsConstructor
public class HitController {
    private final EndpointService endpointService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEndpointHitDto saveEndpoint(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        return endpointService.save(endpointHitDto);
    }
}
