package ru.practicum.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.basic.entity.CompilationEvent;

@Repository
public interface CompilationEventRepository extends JpaRepository<CompilationEvent, Long> {
}
