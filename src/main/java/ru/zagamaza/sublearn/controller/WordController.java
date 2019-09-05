package ru.zagamaza.sublearn.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.infra.service.WordInfraService;

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
