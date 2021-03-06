package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.EpisodeEntity;

import java.util.Optional;

@Repository
public interface EpisodeRepository extends JpaRepository<EpisodeEntity, Long> {

    Page<EpisodeEntity> findAllByCollectionEntityId(Long collectionId, Pageable pageable);

    Page<EpisodeEntity> findAllBySeasonAndCollectionEntityId(Integer season, Long collectionId, Pageable pageable);

    EpisodeEntity findBySeasonAndEpisodeAndCollectionEntityId(Integer season, Integer episode, Long collectionId);

    @Query(value = "select e from EpisodeEntity e join fetch e.worldEntities where e.id = :id")
    Optional<EpisodeEntity> findByIdWithWords(@Param("id") Long id);

    @Query(value = "select distinct e.season from episodes e where e.collection_id = :collectionId order by season",
           countQuery = "select count (distinct e.season) from episodes e where e.collection_id = :collectionId",
           nativeQuery = true)
    Page<Integer> getSeasonsByCollectionId(@Param("collectionId") Long collectionId, Pageable pageable);

    @Query(value = "select (select  sum(case when uw.rate < (:learnCount + 1) then cast(uw.rate as decimal) / (:learnCount) else 1 end)\n" +
            "        from episodes e\n" +
            "                 join episodes_world_entities ewe on e.id = ewe.episode_entity_id\n" +
            "                 join words w on ewe.world_entities_id = w.id\n" +
            "                 join user_word uw on w.id = uw.word_id\n" +
            "        where e.id = :id\n" +
            "          and uw.user_id = :userId) * 100 " +
            "       / (select case when count(1) = 0 then null else count(1) end\n" +
            "                                             from episodes e\n" +
            "                                                      join episodes_world_entities ewe on e.id = ewe.episode_entity_id\n" +
            "                                                      join words w on ewe.world_entities_id = w.id\n" +
            "                                             where e.id = :id);", nativeQuery = true)
    Integer getLearnedPercent(@Param("id") Long id, @Param("userId") Long userId, @Param("learnCount") Integer learnCount);

    @Query(value = "select not exists(select from episodes e " +
            "join episodes_world_entities ewe on e.id = ewe.episode_entity_id " +
            "where ewe.world_entities_id is not null and e.id = :id); ", nativeQuery = true)
    Boolean isEmptyWords(@Param("id") Long id);

}

