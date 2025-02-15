package ru.practicum.basic.service.compilation;

import ru.practicum.basic.dto.compilation.CompilationDto;
import ru.practicum.basic.dto.compilation.NewCompilationDto;
import ru.practicum.basic.dto.compilation.UpdateCompilationRequest;

public interface AdminCompilationService {
    CompilationDto create(NewCompilationDto newCompilationDto);

    void delete(Long id);

    CompilationDto update(Long id, UpdateCompilationRequest updateCompilationRequest);

}
