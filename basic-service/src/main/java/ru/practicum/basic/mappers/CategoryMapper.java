package ru.practicum.basic.mappers;

import ru.practicum.basic.dto.category.CategoryDto;
import ru.practicum.basic.dto.category.NewCategoryDto;
import ru.practicum.basic.entity.Category;

import java.util.Collection;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static Category fromDto(NewCategoryDto newCategoryDto) {
        return new Category(null, newCategoryDto.getName(), null);
    }

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Collection<CategoryDto> toDto(Collection<Category> categories) {
        return categories.stream().map(CategoryMapper::toDto).collect(Collectors.toList());
    }
}
