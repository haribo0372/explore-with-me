package ru.practicum.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.basic.entity.Event;
import ru.practicum.basic.entity.ParticipationRequest;
import ru.practicum.basic.entity.User;
import ru.practicum.basic.models.enums.ParticipationRequestStatus;

import java.util.Collection;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    Collection<ParticipationRequest> findAllByRequesterId(Long requesterId);

    Collection<ParticipationRequest> findAllByEvent(Event event);

    boolean existsByEventAndRequester(Event event, User requester);

    long countByEventIdAndStatus(Long eventId, ParticipationRequestStatus status);

    Collection<ParticipationRequest> findAllByEventIdAndStatus(Long eventId, ParticipationRequestStatus status);
}
