package ru.practicum.basic.service.comment.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.basic.dto.comment.CommentDto;
import ru.practicum.basic.dto.comment.CreateCommentDto;
import ru.practicum.basic.dto.comment.ResponseClearCommentsFromEvent;
import ru.practicum.basic.dto.comment.UpdateCommentDto;
import ru.practicum.basic.entity.Comment;
import ru.practicum.basic.entity.Event;
import ru.practicum.basic.entity.User;
import ru.practicum.basic.exception.models.CommentException;
import ru.practicum.basic.mappers.CommentMapper;
import ru.practicum.basic.models.enums.CommentLifeCycle;
import ru.practicum.basic.repository.CommentRepository;
import ru.practicum.basic.service.base.BaseServiceImpl;
import ru.practicum.basic.service.comment.AdminCommentService;
import ru.practicum.basic.service.comment.PrivateCommentService;
import ru.practicum.basic.service.comment.PublicCommentService;
import ru.practicum.basic.service.event.impl.EventServiceImpl;
import ru.practicum.basic.service.user.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment>
        implements PrivateCommentService, PublicCommentService, AdminCommentService {
    private final CommentRepository commentRepository;

    private final UserServiceImpl userService;
    private final EventServiceImpl eventService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserServiceImpl userService, EventServiceImpl eventService) {
        super(commentRepository, "entity.Comment");
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.eventService = eventService;
    }

    @Override
    public void delete(Long userId, Long commentId) {
        Optional<Comment> commentOpt = commentRepository.findByIdAndState(commentId, CommentLifeCycle.ACTIVE);
        if (commentOpt.isEmpty()
                || (!Objects.equals(commentOpt.get().getUser().getId(), userId)
                && !Objects.equals(commentOpt.get().getEvent().getInitiator().getId(), userId))) {
            throw new CommentException(
                    format("Комментарий с id = %d не найден или недоступен", commentId),
                    HttpStatus.NOT_FOUND);
        }

        Comment comment = commentOpt.get();
        comment.setState(CommentLifeCycle.DELETED);
        commentRepository.save(comment);
    }

    @Override
    public CommentDto createComment(Long userId, CreateCommentDto commentDto) {
        User user = userService.getById(userId);
        Event event = eventService.getByIdIfPublish(commentDto.getEventId());

        Comment comment = CommentMapper.fromDto(commentDto);
        comment.setCreateDate(LocalDateTime.now().withNano(0));
        comment.setState(CommentLifeCycle.ACTIVE);
        comment.setEvent(event);
        comment.setUser(user);

        return CommentMapper.toDto(super.save(comment));
    }

    @Override
    public CommentDto updateComment(Long userId, UpdateCommentDto commentDto) {
        Comment comment = getCommentIfAccessible(userId, commentDto.getId());

        comment.setText(commentDto.getText());
        comment.setUpdateDate(LocalDateTime.now().withNano(0));
        return CommentMapper.toDto(this.save(comment));
    }

    @Override
    public Collection<CommentDto> getAllByUserId(Long userId, int from, int size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        return CommentMapper.toDto(
                commentRepository.findAllByUserIdAndState(userId, CommentLifeCycle.ACTIVE, pageable).getContent()
        );
    }

    @Override
    public CommentDto getById(Long commentId) {
        return CommentMapper.toDto(
                commentRepository.findByIdAndState(commentId, CommentLifeCycle.ACTIVE)
                        .orElseThrow(() -> new CommentException(
                                format("Комментарий с id = %d не найден или недоступен", commentId),
                                HttpStatus.NOT_FOUND))
        );
    }

    @Override
    public Collection<CommentDto> findAllByEventId(Long eventId, int from, int size) {
        Event event = eventService.getByIdIfPublish(eventId);

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        return CommentMapper.toDto(
                commentRepository.findAllByEventIdAndState(
                        event.getId(), CommentLifeCycle.ACTIVE, pageable).getContent());
    }

    private Comment getCommentIfAccessible(Long userId, Long commentId) {
        userService.existsWithId(userId);
        Optional<Comment> commentOpt = commentRepository.findByIdAndState(commentId, CommentLifeCycle.ACTIVE);

        if (commentOpt.isEmpty() || !Objects.equals(commentOpt.get().getUser().getId(), userId))
            throw new CommentException(
                    format("Комментарий с id = %d не найден или недоступен", commentId),
                    HttpStatus.NOT_FOUND);

        return commentOpt.get();
    }

    @Override
    public CommentDto adminFindCommentById(Long commentId) {
        Comment comment = super.findById(commentId);
        return CommentMapper.toDto(comment);
    }

    @Override
    public Collection<CommentDto> adminFindAllByEventIdAndState(Long eventId, CommentLifeCycle state, int from, int size) {
        Event event = eventService.getById(eventId);

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        Page<Comment> comments = state == null ?
                commentRepository.findAllByEventId(event.getId(), pageable) :
                commentRepository.findAllByEventIdAndState(event.getId(), state, pageable);

        return CommentMapper.toDto(comments.getContent());
    }

    @Override
    public void adminDeleteById(Long commentId) {
        super.delete(commentId);
    }

    @Override
    public ResponseClearCommentsFromEvent adminDeleteAllByEventId(Long eventId) {
        Event event = eventService.getById(eventId);
        Integer countDeleted = commentRepository.deleteAndReturnCountByEventId(event.getId());
        return new ResponseClearCommentsFromEvent(event.getId(), countDeleted);
    }
}
