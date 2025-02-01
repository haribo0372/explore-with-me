package ru.practicum.basic.service;

import org.springframework.lang.Nullable;
import ru.practicum.basic.dto.compilation.CompilationDto;
import ru.practicum.basic.dto.compilation.NewCompilationDto;
import ru.practicum.basic.dto.compilation.UpdateCompilationRequest;

import java.util.Collection;

public interface CompilationService {
    CompilationDto create(NewCompilationDto newCompilationDto);

    void delete(Long id);

    CompilationDto update(Long id, UpdateCompilationRequest updateCompilationRequest);

    Collection<CompilationDto> getAll(int from, int size, @Nullable Boolean pinned);

    CompilationDto getById(Long id);
}
