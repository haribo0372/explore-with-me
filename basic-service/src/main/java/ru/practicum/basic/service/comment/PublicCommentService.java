package ru.practicum.basic.service.comment;

import ru.practicum.basic.dto.comment.CommentDto;

import java.util.Collection;

public interface PublicCommentService {
    CommentDto getById(Long commentId);

    Collection<CommentDto> findAllByEventId(Long eventId, int from, int size);

}
