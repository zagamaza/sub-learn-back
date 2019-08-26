package ru.zagamaza.sublearn.infra.dao.repository;

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
            "       join collections c on e.collection_id = c.id\n" +
            "       join trials t on e.id = t.episode_id\n" +
            "       join episodes_world_entities ewe on e.id = ewe.episode_entity_id\n" +
            "where t.id = :trialId\n" +
            "  and  not exists(select *\n" +
            "                  from episodes e\n" +
            "                         join collections c on e.collection_id = c.id\n" +
            "                         join trials t2 on e.id = t2.episode_id\n" +
            "                         join episodes_world_entities ewe2 on e.id = ewe2.episode_entity_id\n" +
            "                         join trial_word tw on t2.id = tw.trial_id\n" +
            "                  where t.id = t2.id and ewe.world_entities_id = tw.word_id and tw.is_passed is true)\n" +
            "order by random() limit 20;\n", nativeQuery = true)
    List<Long> getWordIdsForTrial(@Param("trialId") Long trialId);


}

