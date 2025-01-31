package ru.practicum.basic.controller.privatte;

import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.event.participation.ParticipationRequestDto;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/requests")
public class UsersRequestsController {
    @GetMapping
    public Collection<ParticipationRequestDto> getRequestsByUserId(@PathVariable Long userId) {
        return null;
    }

    @PostMapping
    public ParticipationRequestDto addRequestByUserId(@PathVariable Long userId,
                                                      @RequestParam Long eventId) {
        return null;
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequestByUserId(@PathVariable Long userId,
                                                         @PathVariable Long requestId) {
        return null;
    }
}
