package ru.practicum.basic.service.category.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.practicum.basic.dto.category.CategoryDto;
import ru.practicum.basic.dto.category.NewCategoryDto;
import ru.practicum.basic.entity.Category;
import ru.practicum.basic.exception.models.WrongRequestParam;
import ru.practicum.basic.mappers.CategoryMapper;
import ru.practicum.basic.repository.CategoryRepository;
import ru.practicum.basic.service.base.BaseServiceImpl;
import ru.practicum.basic.service.category.AdminCategoryService;
import ru.practicum.basic.service.category.PublicCategoryService;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static java.lang.String.format;
import static ru.practicum.basic.mappers.CategoryMapper.toDto;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category>
        implements AdminCategoryService, PublicCategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository repository) {
        super(repository, "entity.Category");
        categoryRepository = repository;
    }

    @Override
    public CategoryDto create(NewCategoryDto newCategoryDto) {
        if (categoryRepository.findByName(newCategoryDto.getName()).isPresent())
            throw new WrongRequestParam(
                    format("Категория с name='%s' уже существует", newCategoryDto.getName()), HttpStatus.CONFLICT);

        Category category = CategoryMapper.fromDto(newCategoryDto);
        return toDto(super.save(category));
    }

    @Override
    public void delete(Long id) {
        Category category = super.findById(id);
        if (!category.getEvents().isEmpty())
            throw new WrongRequestParam("Категория содержит события", HttpStatus.CONFLICT);
        super.delete(id);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto newData) {
        Category category = super.findById(id);

        Optional<Category> foundByName = categoryRepository.findByName(newData.getName());
        if (foundByName.isPresent() && !Objects.equals(foundByName.get().getId(), category.getId()))
            throw new WrongRequestParam(
                    format("Категория с name='%s' уже существует", newData.getName()), HttpStatus.CONFLICT);

        category.setName(newData.getName());
        return toDto(super.save(category));
    }

    @Override
    public Collection<CategoryDto> getAll(int from, int size) {
        return toDto(super.findAll(from, size));
    }

    @Override
    public CategoryDto getById(Long id) {
        return toDto(super.findById(id));
    }

    public Category getCategoryById(Long id) {
        return super.findById(id);
    }
}
