package ru.practicum.basic.controller.publicc;

import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.category.CategoryDto;

import java.util.Collection;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    @GetMapping
    public Collection<CategoryDto> getCategories(@RequestParam(required = false, defaultValue = "0") int from,
                                                 @RequestParam(required = false, defaultValue = "10") int size) {
        return null;
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable long catId) {
        return null;
    }
}
