package ru.zagamaza.sublearn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.zagamaza.sublearn.dto.UserDto;
import ru.zagamaza.sublearn.dto.UserRequest;
import ru.zagamaza.sublearn.infra.service.UserInfraService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserInfraService service;

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/telegram/{telegramId}")
    public UserDto getByTelegramId(@PathVariable Long telegramId) {
        return service.getByTelegramId(telegramId);
    }

    @GetMapping("/")
    public List<UserDto> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping
    public UserDto getByUserName(@RequestParam String userName) {
        return service.getByUserName(userName);
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserRequest userRequest) {
        UserDto userDto = UserDto.from(userRequest);
        return service.save(userDto);
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody UserRequest userRequest) {
        UserDto userDto = UserDto.from(userRequest);
        return service.save(userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }

}

