package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.WordEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorldRepository extends JpaRepository<WordEntity, Long> {

    Optional<WordEntity> findByWord(String word);

    @Query(value = "select world_entities_id\n" +
            "from episodes e\n" +
            "       join episodes_world_entities ewe on e.id = ewe.episode_entity_id\n" +
            "where e.id = :episodeId and ewe.world_entities_id != :exclusionWordId\n" +
            "order by random()\n" +
            "         limit :countWord", nativeQuery = true)
    List<Long> findRandomWordsByEpisodeIdAndExcludeWord(
            @Param("episodeId") Long episodeId,
            @Param("exclusionWordId") Long exclusionWordId,
            @Param("countWord") Integer countWord
    );

}

