package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.WordEntity;

import java.util.Optional;

@Repository
public interface WorldRepository extends JpaRepository<WordEntity, Long> {
    Optional<WordEntity> findByWord(String word);

}

