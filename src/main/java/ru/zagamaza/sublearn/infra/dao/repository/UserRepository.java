package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByTelegramId(Long telegramId);

    @Query(value = "select t.user_id from trials t " +
            "       where t.id = :trialId limit 1; ", nativeQuery = true)
    Long getIdByTrialId(@Param("trialId") Long trialId);

}

