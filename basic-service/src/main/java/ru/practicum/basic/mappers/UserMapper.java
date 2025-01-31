package ru.practicum.basic.mappers;

import ru.practicum.basic.dto.user.NewUserRequest;
import ru.practicum.basic.dto.user.UserDto;
import ru.practicum.basic.dto.user.UserShortDto;
import ru.practicum.basic.entity.User;

public class UserMapper {
    public static User fromNewUserRequest(NewUserRequest dto) {
        return new User(null, dto.getName(), dto.getEmail());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }

    public User fromUserShortDto(UserShortDto dto) {
        return new User(dto.getId(), dto.getName(), null);
    }
}
