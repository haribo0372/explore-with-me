package ru.practicum.basic.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.basic.dto.user.NewUserRequest;
import ru.practicum.basic.dto.user.UserDto;
import ru.practicum.basic.entity.User;
import ru.practicum.basic.exception.models.NotFoundException;
import ru.practicum.basic.exception.models.UserAdminException;
import ru.practicum.basic.mappers.UserMapper;
import ru.practicum.basic.repository.UserRepository;
import ru.practicum.basic.service.base.BaseServiceImpl;
import ru.practicum.basic.service.user.AdminUserService;

import java.util.Collection;

import static java.lang.String.format;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements AdminUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        super(repository, "entity.User");
        this.userRepository = repository;
    }

    @Override
    public UserDto create(NewUserRequest newUserRequest) {
        if (userRepository.findByEmail(newUserRequest.getEmail()).isPresent())
            throw new UserAdminException("Пользватель с таким email уже существует", HttpStatus.CONFLICT);

        User savedUser = super.save(UserMapper.fromNewUserRequest(newUserRequest));
        return UserMapper.toUserDto(savedUser);
    }

    @Override
    public Collection<UserDto> getAll(Collection<Long> userIds, int from, int size) {
        if (userIds != null)
            return UserMapper.toUserDto(super.findAllById(userIds));
        return UserMapper.toUserDto(super.findAll(from, size));
    }


    public User getById(Long id) {
        return super.findById(id);
    }

    public void existsWithId(Long userId) {
        if (!userRepository.existsById(userId))
            throw new NotFoundException(
                    format("entity.User{id=%d} не найден", userId), HttpStatus.NOT_FOUND);
    }

    @Override
    public void delete(Long userId) {
        super.delete(userId);
    }
}
