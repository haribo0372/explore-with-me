package ru.practicum.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.basic.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    Optional<Category> findByName(String name);
}
