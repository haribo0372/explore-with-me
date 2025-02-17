package ru.practicum.basic.mappers;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.basic.dto.user.NewUserRequest;
import ru.practicum.basic.dto.user.UserDto;
import ru.practicum.basic.dto.user.UserShortDto;
import ru.practicum.basic.entity.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class UserMapper {
    public static User fromNewUserRequest(NewUserRequest dto) {
        User user = new User(null, dto.getName(), dto.getEmail());
        log.debug("mappers.UserMapper.fromNewUserRequest({}) -> {}", dto, user);
        return user;
    }

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        log.debug("mappers.UserMapper.toUserDto({}) -> {}", user, userDto);
        return userDto;
    }

    public static Collection<UserDto> toUserDto(Collection<User> users) {
        return users.stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    public User fromUserShortDto(UserShortDto dto) {
        User user = new User(dto.getId(), dto.getName(), null);
        log.debug("mappers.UserMapper.fromUserShortDto({}) -> {}", dto, user);
        return user;
    }

    public static UserShortDto toUserShortDto(User user) {
        UserShortDto userShortDto = new UserShortDto(user.getId(), user.getName());
        log.debug("mappers.UserMapper.toUserShortDto({}) -> {}", user, userShortDto);
        return userShortDto;
    }
}
