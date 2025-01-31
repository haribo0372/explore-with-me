package ru.practicum.basic.service;

import ru.practicum.basic.dto.event.compilation.CompilationDto;
import ru.practicum.basic.dto.event.compilation.NewCompilationDto;
import ru.practicum.basic.dto.event.compilation.UpdateCompilationRequest;

public interface CompilationService {
    CompilationDto create(NewCompilationDto newCompilationDto);

    void delete(Long id);

    CompilationDto update(Long id, UpdateCompilationRequest updateCompilationRequest);
}
