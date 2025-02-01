package ru.practicum.basic.controller.privatte;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.participation.ParticipationRequestDto;
import ru.practicum.basic.service.participation.UserParticipationRequestService;

import java.util.Collection;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class UsersRequestsController {

    private final UserParticipationRequestService participationRequestService;

    @GetMapping
    public Collection<ParticipationRequestDto> getRequestsByUserId(@PathVariable Long userId) {
        return participationRequestService.getAllRequestsOfUser(userId);
    }

    @PostMapping
    public ParticipationRequestDto addRequestByUserId(@PathVariable Long userId,
                                                      @RequestParam Long eventId) {
        return participationRequestService.create(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequestByUserId(@PathVariable Long userId,
                                                         @PathVariable Long requestId) {
        return participationRequestService.cancelRequest(userId, requestId);
    }
}
