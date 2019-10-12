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
            "         join collections c on e.collection_id = c.i" +
            "d\n" +
            "         join trials t on e.id = t.episode_id\n" +
            "         join episodes_world_entities ewe on e.id = ewe.episode_entity_id\n" +
            "         join words w on ewe.world_entities_id = w.id\n" +
            "where t.id = :trialId\n" +
            "order by not exists(select 1\n" +
            "                    from episodes e\n" +
            "                             join collections c on e.collection_id = c.id\n" +
            "                             join trials t2 on e.id = t2.episode_id\n" +
            "                             join episodes_world_entities ewe2 on e.id = ewe2.episode_entity_id\n" +
            "                             join trial_word tw on t2.id = tw.trial_id\n" +
            "                    where t.id = t2.id\n" +
            "                      and ewe.world_entities_id = tw.word_id\n" +
            "                      and tw.is_passed is true\n" +
            "                      and tw.is_right is false),\n" +
            "         exists(select 1\n" +
            "                from episodes e\n" +
            "                         join collections c on e.collection_id = c.id\n" +
            "                         join trials t2 on e.id = t2.episode_id\n" +
            "                         join episodes_world_entities ewe2 on e.id = ewe2.episode_entity_id\n" +
            "                         join trial_word tw on t2.id = tw.trial_id\n" +
            "                         join user_word u on tw.word_id = u.word_id\n" +
            "                where t.id = t2.id\n" +
            "                  and ewe.world_entities_id = tw.word_id),\n" +
            "         (select rate from user_word uw where c.user_id = uw.user_id and w.id = uw.word_id), random()",
           countQuery = "select ewe.world_entities_id\n" +
                   "from episodes e\n" +
                   "         join collections c on e.collection_id = c.id\n" +
                   "         join trials t on e.id = t.episode_id\n" +
                   "         join episodes_world_entities ewe on e.id = ewe.episode_entity_id\n" +
                   "         join words w on ewe.world_entities_id = w.id\n" +
                   "where t.id = :trialId\n" +
                   "order by not exists(select 1\n" +
                   "                    from episodes e\n" +
                   "                             join collections c on e.collection_id = c.id\n" +
                   "                             join trials t2 on e.id = t2.episode_id\n" +
                   "                             join episodes_world_entities ewe2 on e.id = ewe2.episode_entity_id\n" +
                   "                             join trial_word tw on t2.id = tw.trial_id\n" +
                   "                    where t.id = t2.id\n" +
                   "                      and ewe.world_entities_id = tw.word_id\n" +
                   "                      and tw.is_passed is true\n" +
                   "                      and tw.is_right is false),\n" +
                   "         exists(select 1\n" +
                   "                from episodes e\n" +
                   "                         join collections c on e.collection_id = c.id\n" +
                   "                         join trials t2 on e.id = t2.episode_id\n" +
                   "                         join episodes_world_entities ewe2 on e.id = ewe2.episode_entity_id\n" +
                   "                         join trial_word tw on t2.id = tw.trial_id\n" +
                   "                         join user_word u on tw.word_id = u.word_id\n" +
                   "                where t.id = t2.id\n" +
                   "                  and ewe.world_entities_id = tw.word_id),\n" +
                   "         (select rate from user_word uw where c.user_id = uw.user_id and w.id = uw.word_id), random()",
           nativeQuery = true)
    List<Long> getWordIdsForTrial(@Param("trialId") Long trialId, Pageable pageable);

    @Query(value = "select c.user_id\n" +
            "       from trial_word tw\n" +
            "       join trials t on tw.trial_id = t.id\n" +
            "       join episodes e on t.episode_id = e.id\n" +
            "       join collections c on e.collection_id = c.id\n" +
            "       where tw.id = :trialWordId limit 1;", nativeQuery = true)
    Long getUserIdById(@Param("trialWordId") Long trialWordId);

}

