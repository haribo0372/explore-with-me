package ru.practicum.basic.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.event.compilation.CompilationDto;
import ru.practicum.basic.dto.event.compilation.NewCompilationDto;
import ru.practicum.basic.dto.event.compilation.UpdateCompilationRequest;
import ru.practicum.basic.service.CompilationService;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationsController {
    private CompilationService compilationService;

    @PostMapping
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto newCategoryDto) {
        return compilationService.create(newCategoryDto);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> deleteCompilation(@PathVariable long compId) {
        compilationService.delete(compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable long compId,
                                            @RequestBody @Valid UpdateCompilationRequest newData) {
        return compilationService.update(compId, newData);
    }
}
