package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.TrialWordEntity;

import java.util.List;

@Repository
public interface TrialWordRepository extends JpaRepository<TrialWordEntity, Long> {

    @Query(value = "select ewe.world_entities_id\n" +
            "from episodes e\n" +
            "         join collections c on e.collection_id = c.id\n" +
            "         join user_to_collection utc on c.id = utc.collection_id\n" +
            "         join trials t on e.id = t.episode_id and utc.user_id = t.user_id\n" +
            "         join episodes_world_entities ewe on e.id = ewe.episode_entity_id\n" +
            "         join words w on ewe.world_entities_id = w.id\n" +
            "where t.id = :trialId\n" +
            "order by (select rate from user_word uw where utc.user_id = uw.user_id and w.id = uw.word_id) nulls first" +
            ", random()",
           countQuery = "select ewe.world_entities_id\n" +
                   "from episodes e\n" +
                   "         join collections c on e.collection_id = c.id\n" +
                   "         join user_to_collection utc on c.id = utc.collection_id\n" +
                   "         join trials t on e.id = t.episode_id and utc.user_id = t.user_id\n" +
                   "         join episodes_world_entities ewe on e.id = ewe.episode_entity_id\n" +
                   "         join words w on ewe.world_entities_id = w.id\n" +
                   "where t.id = :trialId\n" +
                   "order by (select rate from user_word uw where utc.user_id = uw.user_id and w.id = uw.word_id) nulls first" +
                   ", random()",
           nativeQuery = true)
    List<Long> getWordIdsForTrial(@Param("trialId") Long trialId, Pageable pageable);

    @Query(value = "select t.user_id\n" +
            "       from trial_word tw\n" +
            "       join trials t on tw.trial_id = t.id\n" +
            "       where tw.id = :trialWordId limit 1;", nativeQuery = true)
    Long getUserIdById(@Param("trialWordId") Long trialWordId);

}

