package ru.practicum.basic.mappers;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.basic.dto.compilation.CompilationDto;
import ru.practicum.basic.dto.compilation.NewCompilationDto;
import ru.practicum.basic.dto.event.EventShortDto;
import ru.practicum.basic.entity.Compilation;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class CompilationMapper {
    public static Compilation fromDto(NewCompilationDto dto) {
        Compilation compilation =
                new Compilation(null, dto.getTitle(), dto.getPinned());
        log.debug("mappers.CompilationMapper.fromDto({}) -> {}", dto, compilation);
        return compilation;
    }

    public static CompilationDto toDto(Compilation compilation) {
        CompilationDto compilationDto = new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                null);

        Set<EventShortDto> events = compilation.getCompilationEvents()
                .stream()
                .map(i -> EventMapper.toEventShortDto(i.getEvent()))
                .collect(Collectors.toSet());
        compilationDto.setEvents(events);

        log.debug("mappers.CompilationMapper.toDto(Compilation{id={}}) -> {}", compilation, events);
        return compilationDto;
    }

    public static Collection<CompilationDto> toDto(Collection<Compilation> compilations) {
        return compilations.stream().map(CompilationMapper::toDto).collect(Collectors.toSet());
    }
}
