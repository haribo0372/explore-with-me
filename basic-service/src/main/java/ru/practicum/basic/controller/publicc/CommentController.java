package ru.practicum.basic.controller.publicc;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.comment.CommentDto;
import ru.practicum.basic.service.comment.PublicCommentService;

import java.util.Collection;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final PublicCommentService commentService;

    @GetMapping("/event/{eventId}")
    public Collection<CommentDto> getAllByEventId(@PathVariable Long eventId,
                                                  @RequestParam(required = false, defaultValue = "0") int from,
                                                  @RequestParam(required = false, defaultValue = "10") int size) {
        return commentService.findAllByEventId(eventId, from, size);
    }

    @GetMapping("/{commentId}")
    public CommentDto getCommentById(@PathVariable Long commentId) {
        return commentService.getById(commentId);
    }
}
