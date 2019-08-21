package ru.zagamaza.sublearn.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.infra.service.api.WordInfraService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/words")
@RequiredArgsConstructor
public class WordController {

    private final WordInfraService wordInfraService;

    @GetMapping("/{id}")
    public WordDto get(@PathVariable Long id) {
        return wordInfraService.get(id);
    }

    @PostMapping
    public WordDto create(@Valid @RequestBody WordDto dto) {
        return wordInfraService.save(dto);
    }

    @PutMapping
    public WordDto update(@RequestBody WordDto dto) {
        return wordInfraService.save(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        wordInfraService.removeById(id);
    }
}
