package ru.practicum.basic.service.participation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.basic.dto.participation.ParticipationRequestDto;
import ru.practicum.basic.entity.Event;
import ru.practicum.basic.entity.ParticipationRequest;
import ru.practicum.basic.entity.User;
import ru.practicum.basic.exception.models.ConflictParticipationRequestException;
import ru.practicum.basic.exception.models.NotFoundException;
import ru.practicum.basic.models.enums.EventsLifeCycle;
import ru.practicum.basic.repository.ParticipationRequestRepository;
import ru.practicum.basic.service.base.BaseServiceImpl;
import ru.practicum.basic.service.event.impl.EventServiceImpl;
import ru.practicum.basic.service.participation.UserParticipationRequestService;
import ru.practicum.basic.service.user.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;
import static ru.practicum.basic.mappers.ParticipationRequestMapper.toDto;
import static ru.practicum.basic.models.enums.ParticipationRequestStatus.*;

@Service
public class ParticipationRequestServiceImpl extends BaseServiceImpl<ParticipationRequest>
        implements UserParticipationRequestService {

    private final ParticipationRequestRepository participationRequestRepository;
    private final UserServiceImpl userService;
    private final EventServiceImpl eventService;

    @Autowired
    public ParticipationRequestServiceImpl(ParticipationRequestRepository repository, UserServiceImpl userService, EventServiceImpl eventService) {
        super(repository, "entity.ParticipationRequest");
        this.participationRequestRepository = repository;
        this.userService = userService;
        this.eventService = eventService;
    }

    @Override
    public Collection<ParticipationRequestDto> getAllRequestsOfUser(Long userId) {
        return toDto(participationRequestRepository.findAllByRequesterId(userId));
    }

    @Override
    public ParticipationRequestDto create(Long userId, Long eventId) {
        User user = userService.getById(userId);
        Event event = eventService.getById(eventId);

        if (participationRequestRepository.existsByEventAndRequester(event, user)) {
            throw new ConflictParticipationRequestException(
                    format("Заявка на событие Event{id=%d} от User{id=%d} уже существует", eventId, userId));
        }

        if (Objects.equals(event.getInitiator().getId(), user.getId())) {
            throw new ConflictParticipationRequestException(
                    "Инициатор события не может добавить запрос на участие в своём событии");
        }

        if (event.getState() != EventsLifeCycle.PUBLISHED) {
            throw new ConflictParticipationRequestException(
                    format("Невозможно участвовать в неопубликованном событии," +
                            " переданное событие находится в стадии %s", event.getState()));
        }

        if (event.getParticipantLimit() != 0 &&
                event.getConfirmedRequests() == event.getParticipantLimit().longValue()) {
            throw new ConflictParticipationRequestException(
                    "Достигнут лимит запросов на участие в переданном событии", HttpStatus.CONFLICT);
        }

        ParticipationRequest participationRequest = new ParticipationRequest();
        participationRequest.setRequester(user);
        participationRequest.setEvent(event);
        participationRequest.setCreated(LocalDateTime.now().withNano(0));
        if (event.getRequestModeration() && event.getParticipantLimit() != 0) {
            participationRequest.setStatus(PENDING);
        } else {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventService.saveEvent(event);
            participationRequest.setStatus(CONFIRMED);
        }

        return toDto(super.save(participationRequest));
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        userService.existsWithId(userId);

        Optional<ParticipationRequest> requestOpt = participationRequestRepository.findById(requestId);
        if (requestOpt.isEmpty() || !Objects.equals(requestOpt.get().getRequester().getId(), userId))
            throw new NotFoundException(
                    format("Заявка с requestId=%d не найдена или недоступна", requestId));

        var request = requestOpt.get();
        request.setStatus(CANCELED);
        return toDto(super.save(request));
    }
}
