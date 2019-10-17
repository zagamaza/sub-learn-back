package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.EpisodeEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface EpisodeRepository extends JpaRepository<EpisodeEntity, Long> {

    List<EpisodeEntity> findAllByCollectionEntityId(Long collectionId, Pageable pageable);

    @Query(value = "select e from EpisodeEntity e join fetch e.worldEntities where e.id = :id")
    Optional<EpisodeEntity> findByIdWithWords(@Param("id") Long id);

    @Query(value = "select (select count(1)\n" +
            "        from episodes e\n" +
            "                 join episodes_world_entities ewe on e.id = ewe.episode_entity_id\n" +
            "                 join words w on ewe.world_entities_id = w.id\n" +
            "                 join collections c on e.collection_id = c.id\n" +
            "                 join user_word uw on w.id = uw.word_id and uw.user_id = c.user_id\n" +
            "        where e.id = :id\n" +
            "          and uw.rate > 5) * 100 / (select case when count(1) = 0 then null else count(1) end\n" +
            "                                    from episodes e\n" +
            "                                             join episodes_world_entities ewe on e.id = ewe.episode_entity_id\n" +
            "                                             join words w on ewe.world_entities_id = w.id\n" +
            "                                    where e.id = :id);", nativeQuery = true)
    Integer getLearnedPercent(@Param("id") Long id);

}

