package ru.practicum.basic.service.comment;

import ru.practicum.basic.dto.comment.CommentDto;
import ru.practicum.basic.dto.comment.ResponseClearCommentsFromEvent;
import ru.practicum.basic.models.enums.CommentLifeCycle;

import java.util.Collection;

public interface AdminCommentService {
    CommentDto adminFindCommentById(Long commentId);

    Collection<CommentDto> adminFindAllByEventIdAndState(
            Long eventId, CommentLifeCycle state, int from, int size);

    void adminDeleteById(Long commentId);

    ResponseClearCommentsFromEvent adminDeleteAllByEventId(Long eventId);
}
