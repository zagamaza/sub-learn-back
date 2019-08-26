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

    private final WordInfraService service;

    @GetMapping("/{id}")
    public WordDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public WordDto create(@Valid @RequestBody WordDto wordDto) {
        return service.save(wordDto);
    }

    @PutMapping
    public WordDto update(@RequestBody WordDto wordDto) {
        return service.save(wordDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }
}
