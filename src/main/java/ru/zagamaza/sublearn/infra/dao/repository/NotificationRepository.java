package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.NotificationEntity;
import ru.zagamaza.sublearn.infra.dao.entity.NotificationType;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> findAllByUserEntityId(Long userId);

    Page<NotificationEntity> findAllByNotificationType(NotificationType notificationType, Pageable pageable);

    void removeAllByIdIn(List<Long> ids);

}
