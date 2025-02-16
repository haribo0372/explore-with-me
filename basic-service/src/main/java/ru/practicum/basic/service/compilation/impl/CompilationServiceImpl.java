package ru.practicum.basic.service.compilation.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.basic.dto.compilation.CompilationDto;
import ru.practicum.basic.dto.compilation.NewCompilationDto;
import ru.practicum.basic.dto.compilation.UpdateCompilationRequest;
import ru.practicum.basic.entity.Compilation;
import ru.practicum.basic.entity.CompilationEvent;
import ru.practicum.basic.mappers.CompilationMapper;
import ru.practicum.basic.repository.CompilationRepository;
import ru.practicum.basic.repository.EventRepository;
import ru.practicum.basic.service.base.BaseServiceImpl;
import ru.practicum.basic.service.compilation.AdminCompilationService;
import ru.practicum.basic.service.compilation.PublicCompilationService;

import java.util.Collection;
import java.util.Set;

@Slf4j
@Service
public class CompilationServiceImpl extends BaseServiceImpl<Compilation>
        implements AdminCompilationService, PublicCompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CompilationServiceImpl(CompilationRepository repository, EventRepository eventRepository) {
        super(repository, "entity.Compilation");
        this.compilationRepository = repository;
        this.eventRepository = eventRepository;
    }

    @Override
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        final Compilation compilation = CompilationMapper.fromDto(newCompilationDto);
        this.linkCompilationWithEventsByEventIds(compilation, newCompilationDto.getEventIds());
        CompilationDto result = CompilationMapper.toDto(super.save(compilation));
        log.debug("CompilationServiceImpl.create({}) -> {}", newCompilationDto, result);
        return result;
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
        log.debug("CompilationServiceImpl.delete({}) -> void", id);
    }

    @Override
    public CompilationDto update(Long id, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = super.findById(id);
        if (updateCompilationRequest.getPinned() != null) {
            compilation.setPinned(updateCompilationRequest.getPinned());
        }
        if (updateCompilationRequest.getTitle() != null) {
            compilation.setTitle(updateCompilationRequest.getTitle());
        }
        if (updateCompilationRequest.getEventIds() != null) {
            compilation.getCompilationEvents().clear();
            this.linkCompilationWithEventsByEventIds(compilation, updateCompilationRequest.getEventIds());
        }

        CompilationDto result = CompilationMapper.toDto(super.save(compilation));
        log.debug("CompilationServiceImpl.update({}, {}) -> {}", id, updateCompilationRequest, result);
        return result;
    }

    @Override
    public Collection<CompilationDto> getAll(int from, int size, Boolean pinned) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        Collection<CompilationDto> result = pinned != null ?
                CompilationMapper.toDto(compilationRepository.findByPinned(pinned, pageable).getContent()) :
                CompilationMapper.toDto(compilationRepository.findAll(pageable).getContent());

        log.debug("CompilationServiceImpl.getAll({}, {}, {}) -> CompilationDto[size={}]",
                from, size, pinned, result.size());
        return result;
    }

    @Override
    public CompilationDto getById(Long id) {
        CompilationDto result = CompilationMapper.toDto(super.findById(id));
        log.debug("CompilationServiceImpl.getById({}) -> {}", id, result);
        return result;
    }

    private void linkCompilationWithEventsByEventIds(final Compilation compilation, final Set<Long> eventIds) {
        eventIds.forEach(eventId ->
                eventRepository.findById(eventId).ifPresent(event -> {
                    CompilationEvent compilationEvent =
                            new CompilationEvent(null, compilation, event);
                    log.debug("Связь подборки Compilation{id={}, title={}} с событием Event{id={}, title={}}",
                            compilation.getId(), compilation.getTitle(), event.getId(), event.getTitle());

                    compilation.getCompilationEvents().add(compilationEvent);
                })
        );
    }
}
