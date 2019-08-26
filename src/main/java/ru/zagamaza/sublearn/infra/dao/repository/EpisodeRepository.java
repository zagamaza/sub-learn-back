package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.EpisodeEntity;
import ru.zagamaza.sublearn.infra.dao.entity.TrialWordEntity;

@Repository
public interface EpisodeRepository extends JpaRepository<EpisodeEntity, Long> {
}

