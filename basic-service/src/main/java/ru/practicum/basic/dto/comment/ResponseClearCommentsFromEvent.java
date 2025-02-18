package ru.practicum.basic.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseClearCommentsFromEvent {
    public Long eventId;

    @JsonProperty("numberOfDeletedComments")
    public Integer countComments;
}
