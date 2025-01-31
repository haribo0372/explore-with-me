package ru.practicum.basic.controller.publicc;

import org.springframework.web.bind.annotation.*;
import ru.practicum.basic.dto.event.compilation.CompilationDto;

import java.util.Collection;

@RestController
@RequestMapping("/compilations")
public class CompilationsController {
    @GetMapping
    public Collection<CompilationDto> getCompilations(@RequestParam(required = false, defaultValue = "0") int from,
                                                      @RequestParam(required = false, defaultValue = "10") int size,
                                                      @RequestParam(required = false) Boolean pinned) {
        return null;
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable long compId) {
        return null;
    }
}
