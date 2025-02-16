package ru.practicum.basic.service.user;

import org.springframework.lang.Nullable;
import ru.practicum.basic.dto.user.NewUserRequest;
import ru.practicum.basic.dto.user.UserDto;

import java.util.Collection;

public interface AdminUserService {
    UserDto create(NewUserRequest newUserRequest);

    Collection<UserDto> getAll(@Nullable Collection<Long> userIds, int from, int size);

    void delete(Long userId);

}
