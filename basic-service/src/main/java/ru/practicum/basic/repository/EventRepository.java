package ru.practicum.basic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.basic.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    @Query("""
                SELECT e FROM Event e
                WHERE (:users IS NULL OR e.initiator.id IN :users)
                AND (:states IS NULL OR e.state IN :states)
                AND (:categories IS NULL OR e.category.id IN :categories)
                AND (e.eventDate >= CAST(:rangeStart AS timestamp))
                AND (e.eventDate <= CAST(:rangeEnd AS timestamp))
            """)
    List<Event> universalSearch(@Param("users") List<Long> users,
                                @Param("states") List<String> states,
                                @Param("categories") List<Long> categories,
                                @Param("rangeStart") LocalDateTime rangeStart,
                                @Param("rangeEnd") LocalDateTime rangeEnd,
                                Pageable pageable);

    @Query("""
                SELECT e FROM Event e
                WHERE e.state = 'PUBLISHED'
                AND (:ext IS NULL OR LOWER(e.annotation) LIKE LOWER(CONCAT('%', :ext, '%'))\s
                                      OR LOWER(e.description) LIKE LOWER(CONCAT('%', :ext, '%')))
                AND (:categories IS NULL OR e.category.id IN :categories)
                AND (:paid IS NULL OR e.paid = :paid)
                AND (e.eventDate >= CAST(:rangeStart AS timestamp))
                AND (e.eventDate <= CAST(:rangeEnd AS timestamp))
                AND (:onlyAvailable = FALSE OR e.participantLimit IS NULL OR e.participantLimit > e.confirmedRequests)
            """)
    Page<Event> searchEvents(@Param("ext") String ext,
                             @Param("categories") List<Integer> categories,
                             @Param("paid") Boolean paid,
                             @Param("rangeStart") LocalDateTime rangeStart,
                             @Param("rangeEnd") LocalDateTime rangeEnd,
                             @Param("onlyAvailable") Boolean onlyAvailable,
                             Pageable pageable);
}
