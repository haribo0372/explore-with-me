package ru.practicum.basic.service.category;

import ru.practicum.basic.dto.category.CategoryDto;

import java.util.Collection;

public interface PublicCategoryService {

    Collection<CategoryDto> getAll(int from, int size);

    CategoryDto getById(Long id);

}
