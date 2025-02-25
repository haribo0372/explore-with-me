package ru.practicum.basic.service.base;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.basic.entity.BaseEntity;
import ru.practicum.basic.exception.models.NotFoundException;

import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseServiceImpl<T extends BaseEntity> {
    protected final JpaRepository<T, Long> repository;
    private final String entityNameForLog;

    protected T save(T entity) {
        T saved = repository.save(entity);
        log.debug("{}{id={}} успешно сохранен", entityNameForLog, saved.getId());
        return saved;
    }

    protected T findById(Long id) {
        T found = repository.findById(id).orElseThrow(() -> {
            String message = format("%s{id=%s} не найден", entityNameForLog, id);
            log.info(message);
            return new NotFoundException(message);
        });
        log.debug("{}{id={}} успешно найден", entityNameForLog, id);

        return found;
    }

    protected Collection<T> findAll() {
        List<T> found = repository.findAll();
        log.debug("findAll() -> Все объекты {} возвращены {}[size={}]", entityNameForLog, entityNameForLog, found.size());
        return found;
    }

    protected Collection<T> findAll(int from, int size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        List<T> found = repository.findAll(pageable).getContent();

        log.debug("findAll(from={}, size={}) -> Все объекты {} возвращены {}[size={}]",
                from, size, entityNameForLog, entityNameForLog, found.size());

        return found;
    }

    protected Collection<T> findAllById(Iterable<Long> ids) {
        List<T> found = repository.findAllById(ids);
        log.debug("findAllById(ids={}) -> Все объекты {} возвращены {}[size={}]",
                ids, entityNameForLog, entityNameForLog, found.size());

        return found;
    }

    protected void delete(Long id) {
        T entity = this.findById(id);
        repository.delete(entity);
        log.debug("{}{id={}} успешно удален", entityNameForLog, id);
    }
}
