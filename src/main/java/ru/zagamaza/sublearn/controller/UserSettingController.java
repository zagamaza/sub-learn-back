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
import ru.zagamaza.sublearn.dto.UserSettingDto;
import ru.zagamaza.sublearn.infra.service.UserSettingInfraService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user_settings")
@RequiredArgsConstructor
public class UserSettingController {

    private final UserSettingInfraService service;

    @GetMapping("/{id}")
    public UserSettingDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/users/{userId}")
    public UserSettingDto getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @PostMapping
    public UserSettingDto create(@Valid @RequestBody UserSettingDto userSettingDto) {
        return service.save(userSettingDto);
    }

    @PutMapping
    public UserSettingDto update(@RequestBody UserSettingDto userSettingDto) {
        return service.update(userSettingDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }

}
