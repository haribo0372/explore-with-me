package ru.practicum.basic.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.comment.CommentDto;
import ru.practicum.basic.dto.comment.ResponseClearCommentsFromEvent;
import ru.practicum.basic.models.enums.CommentLifeCycle;
import ru.practicum.basic.service.comment.AdminCommentService;

import java.util.Collection;

@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
public class AdminCommentsController {
    private final AdminCommentService commentService;

    @GetMapping("/{commentId}")
    public CommentDto findCommentById(@PathVariable Long commentId) {
        System.out.println(commentId);
        return commentService.adminFindCommentById(commentId);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentFromDb(@PathVariable Long commentId) {
        commentService.adminDeleteById(commentId);
    }

    @GetMapping("/event/{eventId}")
    public Collection<CommentDto> getAllByEvent(@PathVariable Long eventId,
                                                @RequestParam(required = false) CommentLifeCycle state,
                                                @RequestParam(required = false, defaultValue = "0") int from,
                                                @RequestParam(required = false, defaultValue = "10") int size) {
        return commentService.adminFindAllByEventIdAndState(eventId, state, from, size);
    }

    @DeleteMapping("/event/{eventId}")
    public ResponseClearCommentsFromEvent deleteAllCommentsOfEvent(@PathVariable Long eventId) {
        return commentService.adminDeleteAllByEventId(eventId);
    }
}
