package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zagamaza.sublearn.infra.dao.entity.UserSettingEntity;

import java.util.Optional;

public interface UserSettingRepository extends JpaRepository<UserSettingEntity, Long> {

    Optional<UserSettingEntity> findByUserEntityId(Long userId);

}
