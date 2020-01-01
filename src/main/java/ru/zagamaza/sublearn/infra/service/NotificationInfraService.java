package ru.zagamaza.sublearn.infra.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.dto.NotificationDto;
import ru.zagamaza.sublearn.infra.dao.entity.NotificationType;

import java.util.List;

public interface NotificationInfraService {

    NotificationDto get(Long id);

    NotificationDto update(NotificationDto dto);

    NotificationDto save(NotificationDto dto);

    void saveNotificationForAllUsers(String textMessage);

    Page<NotificationDto> getCallbackNotifications(NotificationType notificationType, Pageable pageable);

    void removeById(long id);

    @Transactional
    void removeByIds(List<Long> ids);

    List<NotificationDto> getByUserId(Long id);

}
