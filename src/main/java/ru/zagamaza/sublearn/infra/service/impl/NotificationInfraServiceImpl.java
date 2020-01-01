package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.dto.NotificationDto;
import ru.zagamaza.sublearn.dto.UserDto;
import ru.zagamaza.sublearn.exception.domain.NotFoundException;
import ru.zagamaza.sublearn.infra.dao.entity.NotificationEntity;
import ru.zagamaza.sublearn.infra.dao.entity.NotificationType;
import ru.zagamaza.sublearn.infra.dao.entity.UserEntity;
import ru.zagamaza.sublearn.infra.dao.repository.NotificationRepository;
import ru.zagamaza.sublearn.infra.service.NotificationInfraService;
import ru.zagamaza.sublearn.infra.service.UserInfraService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationInfraServiceImpl implements NotificationInfraService {

    private final NotificationRepository repository;
    private final UserInfraService userInfraService;
    private final MessageSource messageSource;

    @Override
    public NotificationDto get(Long id) {
        NotificationEntity entity = repository.findById(id)
                                              .orElseThrow(() -> new NotFoundException(
                                                      getMessage("notification.not.found.exception", id)
                                              ));
        return NotificationDto.from(entity);
    }

    @Override
    @Transactional
    public NotificationDto update(NotificationDto dto) {
        NotificationDto notificationDto = get(dto.getId());
        notificationDto.setText(dto.getText());
        return save(notificationDto);
    }

    @Override
    public NotificationDto save(NotificationDto dto) {
        NotificationEntity entity = NotificationEntity.from(dto);
        entity = repository.save(entity);
        return NotificationDto.from(entity);
    }

    @Override
    public void saveNotificationForAllUsers(String textMessage) {
        List<UserDto> allUsers = userInfraService.getAll(PageRequest.of(0, Integer.MAX_VALUE));
        List<NotificationEntity> entities =
                allUsers.stream()
                        .map(u -> NotificationEntity.builder()
                                                    .userEntity(UserEntity
                                                                        .builder()
                                                                        .id(u.getId())
                                                                        .build())
                                                    .created(LocalDateTime.now())
                                                    .notificationType(NotificationType.MESSAGE)
                                                    .build())
                        .collect(Collectors.toList());

        repository.saveAll(entities);
    }

    @Override
    public Page<NotificationDto> getCallbackNotifications(NotificationType notificationType, Pageable pageable) {
        Page<NotificationEntity> entities = repository.findAllByNotificationType(notificationType, pageable);
        return new PageImpl<>(
                entities.stream()
                        .map(NotificationDto::from)
                        .collect(Collectors.toList()),
                pageable,
                entities.getTotalElements()
        );
    }

    @Override
    @Transactional
    public void removeById(long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void removeByIds(List<Long> ids) {
        repository.removeAllByIdIn(ids);
    }

    @Override
    public List<NotificationDto> getByUserId(Long userId) {
        List<NotificationEntity> entities = repository.findAllByUserEntityId(userId);
        return entities.stream()
                       .map(NotificationDto::from)
                       .collect(Collectors.toList());
    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
