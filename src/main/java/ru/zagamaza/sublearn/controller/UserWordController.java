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
import ru.zagamaza.sublearn.dto.UserWordDto;
import ru.zagamaza.sublearn.dto.UserWordRequest;
import ru.zagamaza.sublearn.infra.service.UserWordInfraService;

@RestController
@RequestMapping("/api/user_words")
@RequiredArgsConstructor
public class UserWordController {

    private final UserWordInfraService service;

    @GetMapping("/{id}")
    public UserWordDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public UserWordDto create(@RequestBody UserWordRequest userWordRequest) {
        UserWordDto userWordDto = UserWordDto.from(userWordRequest);
        return service.save(userWordDto);
    }

    @PutMapping
    public UserWordDto update(@RequestBody UserWordRequest userWordRequest) {
        UserWordDto userWordDto = UserWordDto.from(userWordRequest);
        return service.save(userWordDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }

}
