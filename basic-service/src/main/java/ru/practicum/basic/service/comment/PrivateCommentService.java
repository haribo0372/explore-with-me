package ru.practicum.basic.service.comment;

import ru.practicum.basic.dto.comment.CommentDto;
import ru.practicum.basic.dto.comment.CreateCommentDto;
import ru.practicum.basic.dto.comment.UpdateCommentDto;

import java.util.Collection;

public interface PrivateCommentService {

    void delete(Long userId, Long commentId);

    CommentDto createComment(Long userId, CreateCommentDto commentDto);

    CommentDto updateComment(Long userId, UpdateCommentDto commentDto);

    Collection<CommentDto> getAllByUserId(Long userId, int from, int size);
}
