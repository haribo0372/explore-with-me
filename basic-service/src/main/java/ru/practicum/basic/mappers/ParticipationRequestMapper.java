package ru.practicum.basic.mappers;

import ru.practicum.basic.dto.participation.ParticipationRequestDto;
import ru.practicum.basic.entity.ParticipationRequest;

import java.util.Collection;

public class ParticipationRequestMapper {
    public static ParticipationRequestDto toDto(ParticipationRequest request) {
        return new ParticipationRequestDto(
                request.getId(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getCreated(),
                request.getStatus());
    }

    public static Collection<ParticipationRequestDto> toDto(Collection<ParticipationRequest> requests) {
        return requests.stream().map(ParticipationRequestMapper::toDto).toList();
    }
}
