package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.UserWordEntity;

import java.util.Optional;

@Repository
public interface UserWordRepository extends JpaRepository<UserWordEntity, Long> {

    Optional<UserWordEntity> findByWordEntityIdAndUserEntityId(Long wordId, Long userId);

    void deleteAllByUserEntityId(Long userId);

}

