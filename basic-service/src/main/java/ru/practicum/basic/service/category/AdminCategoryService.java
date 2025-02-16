package ru.practicum.basic.service.category;

import ru.practicum.basic.dto.category.CategoryDto;
import ru.practicum.basic.dto.category.NewCategoryDto;

public interface AdminCategoryService {

    CategoryDto create(NewCategoryDto newCategoryDto);

    void delete(Long id);

    CategoryDto update(Long id, CategoryDto newData);

}
