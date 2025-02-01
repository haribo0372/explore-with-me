package ru.practicum.basic.service;

import ru.practicum.basic.dto.user.NewUserRequest;
import ru.practicum.basic.dto.user.UserDto;

import java.util.Collection;

public interface UserService {
    UserDto create(NewUserRequest newUserRequest);

    Collection<UserDto> getAll(Collection<Long> userIds, int from, int size);

    void delete(Long userId);
}
