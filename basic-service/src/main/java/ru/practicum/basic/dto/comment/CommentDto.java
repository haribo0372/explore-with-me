package ru.practicum.basic.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.basic.models.enums.CommentLifeCycle;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String text;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private CommentLifeCycle state;
    private Long userId;
    private Long eventId;
}
