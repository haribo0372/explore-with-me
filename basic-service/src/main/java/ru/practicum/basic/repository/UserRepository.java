package ru.practicum.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.basic.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
