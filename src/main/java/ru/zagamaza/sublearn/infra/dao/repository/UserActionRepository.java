package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.UserActionEntity;

@Repository
public interface UserActionRepository extends JpaRepository<UserActionEntity, Long> {

    Page<UserActionEntity> findAllByUserEntityId(Long userId, Pageable pageable);

    Page<UserActionEntity> findAll(Pageable pageable);

}
