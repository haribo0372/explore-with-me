package ru.practicum.basic.controller.privatte;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.comment.CommentDto;
import ru.practicum.basic.dto.comment.CreateCommentDto;
import ru.practicum.basic.dto.comment.UpdateCommentDto;
import ru.practicum.basic.service.comment.PrivateCommentService;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/comment")
@RequiredArgsConstructor
public class UsersCommentsController {
    private final PrivateCommentService commentService;

    @GetMapping
    private Collection<CommentDto> getAllCommentsOfUser(@PathVariable Long userId,
                                                        @RequestParam(required = false, defaultValue = "0") int from,
                                                        @RequestParam(required = false, defaultValue = "10") int size) {
        return commentService.getAllByUserId(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private CommentDto create(@PathVariable Long userId,
                              @RequestBody @Valid CreateCommentDto commentDto) {
        return commentService.createComment(userId, commentDto);
    }

    @PutMapping
    private CommentDto update(@PathVariable Long userId,
                              @RequestBody @Valid UpdateCommentDto commentDto) {
        return commentService.updateComment(userId, commentDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteComment(@PathVariable Long userId,
                               @PathVariable Long commentId) {
        commentService.delete(userId, commentId);
    }
}
