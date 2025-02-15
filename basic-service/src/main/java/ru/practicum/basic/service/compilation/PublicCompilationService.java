package ru.practicum.basic.service.compilation;

import org.springframework.lang.Nullable;
import ru.practicum.basic.dto.compilation.CompilationDto;

import java.util.Collection;

public interface PublicCompilationService {
    Collection<CompilationDto> getAll(int from, int size, @Nullable Boolean pinned);

    CompilationDto getById(Long id);
}
