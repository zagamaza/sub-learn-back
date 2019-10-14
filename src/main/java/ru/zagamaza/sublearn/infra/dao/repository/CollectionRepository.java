package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.CollectionEntity;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {

    List<CollectionEntity> findAllByUserEntityId(Long userId, Pageable pageable);

    List<CollectionEntity> findAllByNameContainingAndUrlIsNotNullAndIsSharedIsTrue(String name, Pageable pageable);

}

