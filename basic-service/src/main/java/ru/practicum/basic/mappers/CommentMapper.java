package ru.practicum.basic.mappers;

import ru.practicum.basic.dto.comment.CommentDto;
import ru.practicum.basic.dto.comment.CreateCommentDto;
import ru.practicum.basic.entity.Comment;

import java.util.Collection;

public class CommentMapper {
    public static Comment fromDto(CreateCommentDto commentDto) {
        return new Comment(null, commentDto.getText(), null, null, null, null, null);
    }

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getCreateDate(),
                comment.getUpdateDate(),
                comment.getState(),
                comment.getUser() == null ? null : comment.getUser().getId(),
                comment.getEvent() == null ? null : comment.getEvent().getId());
    }

    public static Collection<CommentDto> toDto(Collection<Comment> comments) {
        return comments.stream().map(CommentMapper::toDto).toList();
    }
}
