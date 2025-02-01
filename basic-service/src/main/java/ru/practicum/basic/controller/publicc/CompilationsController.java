package ru.practicum.basic.controller.publicc;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.compilation.CompilationDto;
import ru.practicum.basic.service.CompilationService;

import java.util.Collection;

@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationsController {

    private final CompilationService compilationService;

    @GetMapping
    public Collection<CompilationDto> getCompilations(@RequestParam(required = false, defaultValue = "0") int from,
                                                      @RequestParam(required = false, defaultValue = "10") int size,
                                                      @RequestParam(required = false) Boolean pinned) {
        return compilationService.getAll(from, size, pinned);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable long compId) {
        return compilationService.getById(compId);
    }
}
