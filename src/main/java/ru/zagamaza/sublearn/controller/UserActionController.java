package ru.zagamaza.sublearn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.zagamaza.sublearn.dto.UserActionDto;
import ru.zagamaza.sublearn.infra.service.UserActionInfraService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user_actions")
@RequiredArgsConstructor
public class UserActionController {

    private final UserActionInfraService service;

    @GetMapping("/{id}")
    public UserActionDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public Page<UserActionDto> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/users/{userId}")
    public Page<UserActionDto> getByUserName(@PathVariable Long userId, Pageable pageable) {
        return service.getByUserId(userId, pageable);
    }

    @Async
    @PostMapping
    public void create(@Valid @RequestBody UserActionDto userActionDto) {
        service.save(userActionDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }

}

