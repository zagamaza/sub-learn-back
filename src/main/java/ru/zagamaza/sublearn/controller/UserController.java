package ru.zagamaza.sublearn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.zagamaza.sublearn.dto.UserDto;
import ru.zagamaza.sublearn.infra.service.api.UserInfraService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserInfraService userInfraService;

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        return userInfraService.get(id);
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto userDto) {
        return userInfraService.save(userDto);
    }

    @PutMapping
    public UserDto update(@RequestBody UserDto userDto) {
        return userInfraService.save(userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userInfraService.removeById(id);
    }
}

