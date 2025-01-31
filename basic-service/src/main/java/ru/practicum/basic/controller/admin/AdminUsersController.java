package ru.practicum.basic.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.user.NewUserRequest;
import ru.practicum.basic.dto.user.UserDto;
import ru.practicum.basic.service.UserService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {
    private final UserService userService;

    @GetMapping
    public Collection<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                        @RequestParam(required = false, defaultValue = "0") int from,
                                        @RequestParam(required = false, defaultValue = "0") int size) {
        return userService.getAll(ids, from, size);
    }

    @PostMapping
    public UserDto createNewUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        return userService.save(newUserRequest);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
