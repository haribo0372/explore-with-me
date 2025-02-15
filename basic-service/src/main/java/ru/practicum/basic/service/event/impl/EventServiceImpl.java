package ru.practicum.basic.service.event.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.basic.client.stats.StatsClient;
import ru.practicum.basic.dto.event.*;
import ru.practicum.basic.dto.participation.EventRequestStatusUpdateRequest;
import ru.practicum.basic.dto.participation.EventRequestStatusUpdateResult;
import ru.practicum.basic.dto.participation.ParticipationRequestDto;
import ru.practicum.basic.entity.Category;
import ru.practicum.basic.entity.Event;
import ru.practicum.basic.entity.ParticipationRequest;
import ru.practicum.basic.entity.User;
import ru.practicum.basic.exception.models.EventException;
import ru.practicum.basic.exception.models.NotFoundException;
import ru.practicum.basic.mappers.EventMapper;
import ru.practicum.basic.mappers.ParticipationRequestMapper;
import ru.practicum.basic.models.enums.EventRequestStatus;
import ru.practicum.basic.models.enums.EventsLifeCycle;
import ru.practicum.basic.models.enums.SortPublishEvent;
import ru.practicum.basic.models.enums.StateAction;
import ru.practicum.basic.repository.EventRepository;
import ru.practicum.basic.repository.ParticipationRequestRepository;
import ru.practicum.basic.service.base.BaseServiceImpl;
import ru.practicum.basic.service.category.impl.CategoryServiceImpl;
import ru.practicum.basic.service.event.AdminEventService;
import ru.practicum.basic.service.event.PublicEventService;
import ru.practicum.basic.service.event.UserEventService;
import ru.practicum.basic.service.user.impl.UserServiceImpl;
import ru.practicum.statistics.models.ViewStats;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.String.format;
import static ru.practicum.basic.mappers.EventMapper.toEventFullDto;
import static ru.practicum.basic.mappers.EventMapper.toEventShortDto;
import static ru.practicum.basic.models.enums.ParticipationRequestStatus.CONFIRMED;
import static ru.practicum.basic.models.enums.ParticipationRequestStatus.REJECTED;
import static ru.practicum.basic.models.enums.StateAction.PUBLISH_EVENT;
import static ru.practicum.basic.models.enums.StateAction.REJECT_EVENT;

@Service
public class EventServiceImpl extends BaseServiceImpl<Event>
        implements AdminEventService, UserEventService, PublicEventService {

    private final StatsClient statsClient;

    private final UserServiceImpl userService;
    private final CategoryServiceImpl categoryService;
    private final ParticipationRequestRepository participationRequestRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(StatsClient statsClient, EventRepository repository, UserServiceImpl userService, CategoryServiceImpl categoryService, ParticipationRequestRepository participationRequestRepository) {
        super(repository, "entity.Event");
        this.statsClient = statsClient;
        this.eventRepository = repository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.participationRequestRepository = participationRequestRepository;
    }

    @Override
    public Collection<EventFullDto> searchEvents(List<Long> userIds,
                                                 List<String> states,
                                                 List<Long> categories,
                                                 LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd,
                                                 int from, int size) {
        LocalDateTime start = rangeStart == null ?
                LocalDateTime.of(1999, 1, 16, 0, 0, 0) : rangeStart;
        LocalDateTime end = rangeEnd == null ?
                LocalDateTime.of(9999, 12, 31, 23, 59, 59) : rangeEnd;


        Pageable pageable = PageRequest.of(from / size, size);
        Collection<Event> foundEvents = eventRepository
                .universalSearch(
                        userIds, states,
                        categories, start,
                        end, pageable);

        fillConfirmedRequests(foundEvents);
        return EventMapper.toEventFullDto(foundEvents);
    }

    @Override
    public EventFullDto update(Long id, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = super.findById(id);

        if (updateEventAdminRequest.getEventDate() != null) {
            if (event.getPublishedOn() != null && updateEventAdminRequest.getEventDate().isBefore(event.getPublishedOn().plusHours(1L)))
                throw new EventException("Дата начала изменяемого события должна быть не ранее чем за час от" +
                        " даты публикации.", HttpStatus.CONFLICT);
            event.setEventDate(updateEventAdminRequest.getEventDate());
        }

        StateAction stateAction = updateEventAdminRequest.getStateAction();
        if (stateAction != null) {
            if (stateAction == PUBLISH_EVENT) {
                if (event.getState() != EventsLifeCycle.PENDING) {
                    throw new EventException("Событие можно публиковать, только если оно в состоянии ожидания публикации",
                            HttpStatus.CONFLICT);
                }
                event.setState(EventsLifeCycle.PUBLISHED);
            } else if (stateAction == REJECT_EVENT) {
                if (event.getState() == EventsLifeCycle.PUBLISHED) {
                    throw new EventException("Событие можно отклонить, только если оно еще не опубликовано",
                            HttpStatus.CONFLICT);
                }
                event.setState(EventsLifeCycle.CANCELED);
            }
        }

        if (updateEventAdminRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventAdminRequest.getAnnotation());
        }

        if (updateEventAdminRequest.getCategory() != null) {
            Category category = categoryService.getCategoryById(updateEventAdminRequest.getCategory());
            event.setCategory(category);
        }

        if (updateEventAdminRequest.getDescription() != null) {
            event.setDescription(updateEventAdminRequest.getDescription());
        }

        if (updateEventAdminRequest.getTitle() != null) {
            event.setTitle(updateEventAdminRequest.getTitle());
        }

        if (updateEventAdminRequest.getLocation() != null) {
            Float uLon = updateEventAdminRequest.getLocation().getLon();
            Float uLat = updateEventAdminRequest.getLocation().getLat();
            event.setLocationLon(uLon == null ? event.getLocationLon() : uLon);
            event.setLocationLat(uLat == null ? event.getLocationLat() : uLat);
        }

        if (updateEventAdminRequest.getPaid() != null) {
            event.setPaid(updateEventAdminRequest.getPaid());
        }

        if (updateEventAdminRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        }

        if (updateEventAdminRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        }

        Event saved = super.save(event);
        fillConfirmedRequests(saved);
        return EventMapper.toEventFullDto(saved);
    }

    @Override
    public Collection<EventShortDto> getAll(Long userId, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findAllByInitiatorId(userId, pageable);
        fillConfirmedRequests(events);
        return toEventShortDto(events);
    }

    @Override
    public EventFullDto create(Long userId, NewEventDto newEventDto) {
        User initiator = userService.getById(userId);

        validateEventDate(newEventDto.getEventDate());

        Category category = categoryService.getCategoryById(newEventDto.getCategory());

        Event event = EventMapper.fromDto(newEventDto);
        event.setCategory(category);
        event.setCreatedOn(LocalDateTime.now());
        event.setViews(0L);
        event.setConfirmedRequests(0L);
        event.setState(null);
        event.setInitiator(initiator);
        event.setState(EventsLifeCycle.PENDING);

        Event saved = super.save(event);
        return toEventFullDto(saved);
    }

    @Override
    public EventFullDto getById(Long userId, Long eventId) {
        Event event = getEventIfAccessible(userId, eventId);
        fillConfirmedRequests(event);
        return EventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto update(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event event = getEventIfAccessible(userId, eventId);

        if (event.getState() == EventsLifeCycle.PUBLISHED)
            throw new EventException("Опубликованное событие нельзя изменить", HttpStatus.CONFLICT);

        if (updateEventUserRequest.getEventDate() != null) {
            validateEventDate(updateEventUserRequest.getEventDate());
            event.setEventDate(updateEventUserRequest.getEventDate());
        }

        if (updateEventUserRequest.getTitle() != null)
            event.setTitle(updateEventUserRequest.getTitle());

        if (updateEventUserRequest.getDescription() != null)
            event.setDescription(updateEventUserRequest.getDescription());

        if (updateEventUserRequest.getRequestModeration() != null)
            event.setRequestModeration(updateEventUserRequest.getRequestModeration());

        if (updateEventUserRequest.getPaid() != null)
            event.setPaid(updateEventUserRequest.getPaid());

        if (updateEventUserRequest.getAnnotation() != null)
            event.setAnnotation(updateEventUserRequest.getAnnotation());

        if (updateEventUserRequest.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(updateEventUserRequest.getCategoryId());
            event.setCategory(category);
        }

        if (updateEventUserRequest.getLocation() != null) {
            Float uLon = updateEventUserRequest.getLocation().getLon();
            Float uLat = updateEventUserRequest.getLocation().getLat();
            event.setLocationLon(uLon == null ? event.getLocationLon() : uLon);
            event.setLocationLat(uLat == null ? event.getLocationLat() : uLat);
        }

        if (updateEventUserRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }

        if (updateEventUserRequest.getStateAction() != null) {
            if (updateEventUserRequest.getStateAction() == StateAction.SEND_TO_REVIEW) {
                event.setState(EventsLifeCycle.PENDING);
            } else if (updateEventUserRequest.getStateAction() == StateAction.CANCEL_REVIEW) {
                event.setState(EventsLifeCycle.CANCELED);
            }
        }

        Event saved = super.save(event);
        fillConfirmedRequests(saved);
        return EventMapper.toEventFullDto(saved);
    }

    @Override
    public Collection<ParticipationRequestDto> getRequestInfo(Long userId, Long eventId) {
        return ParticipationRequestMapper.toDto(
                participationRequestRepository.findAllByEvent(getEventIfAccessible(userId, eventId)));
    }

    @Override
    public EventRequestStatusUpdateResult updateRequests(Long userId, Long eventId, EventRequestStatusUpdateRequest updateRequest) {
        Event event = getEventIfAccessible(userId, eventId);
        fillConfirmedRequests(event);
        Collection<ParticipationRequest> allByEvent = participationRequestRepository.findAllByEvent(event);
        EventRequestStatus currentStatus = updateRequest.getStatus();
        boolean flag = currentStatus == EventRequestStatus.CONFIRMED;
        HashSet<Long> requestIds = updateRequest.getRequestIds();

        List<ParticipationRequest> list = allByEvent.stream()
                .filter(i -> requestIds.contains(i.getId()))
                .peek(i -> processRequestApproval(event, i, flag))
                .toList();
        System.out.println("LIST\t" + list);
        participationRequestRepository.saveAll(
                list
        );

        super.save(event);

        return new EventRequestStatusUpdateResult(
                ParticipationRequestMapper.toDto(
                        participationRequestRepository.findAllByEventIdAndStatus(eventId, CONFIRMED)),
                ParticipationRequestMapper.toDto(
                        participationRequestRepository.findAllByEventIdAndStatus(eventId, REJECTED))
        );
    }

    private void processRequestApproval(Event event, ParticipationRequest i, boolean isConfirmed) {
        long confirmedRequests;
        if (isConfirmed) {
            confirmedRequests = event.getConfirmedRequests() + 1;
            if (confirmedRequests > event.getParticipantLimit())
                throw new EventException("Не удалось одобрить заявку, превышен лимит участников", HttpStatus.CONFLICT);
            i.setStatus(CONFIRMED);
        } else {
            if (i.getStatus() == CONFIRMED)
                throw new EventException(
                        format("Не удалось отклонить уже принятую заявку ParticipationRequest{%d}", i.getId()),
                        HttpStatus.CONFLICT
                );

            i.setStatus(REJECTED);
        }
    }

    public Event getById(Long id) {
        Event found = super.findById(id);
        fillConfirmedRequests(found);
        return found;
    }

    private Event getEventIfAccessible(Long userId, Long eventId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty() || !Objects.equals(eventOpt.get().getInitiator().getId(), userId))
            throw new NotFoundException(
                    format("Событие с eventId=%d не найдено или недоступно", eventId));
        return eventOpt.get();
    }

    private void validateEventDate(LocalDateTime eventDate) {
        LocalDateTime minEventDate = LocalDateTime.now().plusHours(2);
        if (eventDate.isBefore(minEventDate))
            throw new EventException(
                    "Значение 'eventDate' не может быть раньше, чем через два часа от текущего момента",
                    HttpStatus.BAD_REQUEST
            );
    }

    @Override
    public Collection<EventShortDto> getAllPublishEvents(String text, ArrayList<Integer> categories,
                                                         Boolean paid, LocalDateTime rangeStart,
                                                         LocalDateTime rangeEnd, Boolean onlyAvailable,
                                                         SortPublishEvent sort, int from, int size,
                                                         HttpServletRequest request) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = rangeStart == null ? now : rangeStart;
        LocalDateTime end = rangeEnd == null ? now.plusYears(1000) : rangeEnd;
        text = text == null ? "" : text;

        Sort sorting = sort == SortPublishEvent.VIEWS ?
                Sort.by("views").descending() : Sort.by("eventDate").descending();

        PageRequest pageRequest = PageRequest.of(from / size, size, sorting);

        Collection<Event> foundEvents = eventRepository.searchEvents(
                        text, categories, paid, start, end, onlyAvailable, pageRequest)
                .getContent().stream().map(event -> {
                    String uri = format("/events/%d", event.getId());
                    statsClient.postHit(uri, request.getRemoteAddr());
                    Collection<ViewStats> stats = statsClient.getStats(LocalDateTime.of(1999, 1, 16, 0, 0),
                            LocalDateTime.now().plusNanos(60), List.of(uri), true);
                    event.setViews(stats.stream().findFirst().get().getHits());
                    Event saved = super.save(event);
                    fillConfirmedRequests(saved);
                    return saved;
                }).toList();
        statsClient.postHit("/events", request.getRemoteAddr());
        return toEventShortDto(foundEvents);
    }

    @Override
    public EventFullDto getPublishEventById(Long eventId, HttpServletRequest request) {
        Event event = super.findById(eventId);

        String uri = format("/events/%d", eventId);
        statsClient.postHit(uri, request.getRemoteAddr());
        Collection<ViewStats> stats = statsClient.getStats(LocalDateTime.of(1999, 1, 16, 0, 0),
                LocalDateTime.now().plusNanos(60), List.of(uri), true);
        event.setViews(stats.stream().findFirst().get().getHits());

        if (event.getState() != EventsLifeCycle.PUBLISHED)
            throw new EventException("Запрашиваемый ресурс не найден или недоступен", HttpStatus.NOT_FOUND);

        Event saved = super.save(event);
        fillConfirmedRequests(saved);
        return EventMapper.toEventFullDto(saved);
    }

    public void saveEvent(Event event) {
        if (event.getId() == null)
            throw new RuntimeException("Сохранение неотслеживаемого события");
        eventRepository.save(event);
    }

    public long getConfirmedRequestsByEventId(Long eventId) {
        return participationRequestRepository.countByEventIdAndStatus(eventId, CONFIRMED);
    }

    private void fillConfirmedRequests(Event event) {
        event.setConfirmedRequests(getConfirmedRequestsByEventId(event.getId()));
    }

    private void fillConfirmedRequests(Iterable<Event> iterable) {
        iterable.forEach(this::fillConfirmedRequests);
    }
}
