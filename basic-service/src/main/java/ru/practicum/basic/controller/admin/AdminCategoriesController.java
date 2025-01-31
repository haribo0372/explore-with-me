package ru.practicum.basic.controller.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.category.CategoryDto;
import ru.practicum.basic.dto.category.NewCategoryDto;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoriesController {
    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        return null;
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long catId) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{catId}")
    public CategoryDto patchCategory(@PathVariable long catId,
                                     @RequestBody CategoryDto newData) {
        return null;
    }
}
