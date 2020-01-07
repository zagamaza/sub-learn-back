package ru.zagamaza.sublearn.controller;


import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
import ru.zagamaza.sublearn.dto.NotificationDto;
import ru.zagamaza.sublearn.infra.dao.entity.NotificationType;
import ru.zagamaza.sublearn.infra.service.NotificationInfraService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationInfraService service;

    @GetMapping("/{id}")
    public NotificationDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/users/{userId}")
    public List<NotificationDto> getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @GetMapping
    public Page<NotificationDto> getNotificationsByType(@RequestParam NotificationType type, Pageable pageable) {
        return service.getCallbackNotifications(type, pageable);
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void getByUserId(
            @ApiParam(value = "Пример сообщения * полужирный * _ italic _ ` шрифт фиксированной ширины ` [ ссылка ] (http://google.com)")
            @RequestParam String notificationText
    ) {
        service.saveNotificationForAllUsers(notificationText);
    }

    @PostMapping
    public NotificationDto create(
            @ApiParam(value = "Пример сообщения * полужирный * _ italic _ ` шрифт фиксированной ширины ` [ ссылка ] (http://google.com)")
            @Valid @RequestBody NotificationDto notificationDto
    ) {
        return service.save(notificationDto);
    }

    @PutMapping
    public NotificationDto update(@RequestBody NotificationDto notificationDto) {
        return service.update(notificationDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByIds(@RequestParam List<Long> ids) {
        service.removeByIds(ids);
    }

}
