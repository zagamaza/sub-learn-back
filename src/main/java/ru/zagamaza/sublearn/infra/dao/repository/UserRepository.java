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

    @Query(value = "select u.id from users u\n" +
            "    join user_to_collection utc on u.id = utc.user_id\n" +
            "    join collections c on u.id = utc.user_id\n" +
            "    join episodes e on c.id = e.collection_id\n" +
            "    join trials t on e.id = t.episode_id\n" +
            "where t.id = :trialId limit 1;", nativeQuery = true)
    Long getIdByTrialId(@Param("trialId") Long trialId);

}

