package ru.practicum.basic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.basic.entity.Comment;
import ru.practicum.basic.models.enums.CommentLifeCycle;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByEventId(Long eventId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.event.id = :eventId")
    @Transactional
    Integer deleteAndReturnCountByEventId(@Param("eventId") Long eventId);

    Page<Comment> findAllByUserIdAndState(Long userId, CommentLifeCycle state, Pageable pageable);

    Page<Comment> findAllByEventIdAndState(Long eventId, CommentLifeCycle state, Pageable pageable);

    Optional<Comment> findByIdAndState(Long id, CommentLifeCycle state);
}
