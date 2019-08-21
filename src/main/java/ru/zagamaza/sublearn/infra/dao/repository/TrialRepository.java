package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.TrialEntity;

import java.util.List;

@Repository
public interface TrialRepository extends JpaRepository<TrialEntity, Long> {

    List<TrialEntity> findAllByUserEntityIdOrderByCreatedDesc(Long userId, Pageable pageable);

    @Query(value = "select w.id \n" +
            "from trials t\n" +
            "         join collections c on t.collection_id = c.id\n" +
            "         join collections_world_entities cwe on c.id = cwe.collection_entity_id\n" +
            "         join words w on cwe.world_entities_id = w.id\n" +
            "where t.id = :trialId\n" +
            "  and not exists(select\n" +
            "                 from trials t2\n" +
            "                          join results r on t2.id = r.trial_id\n" +
            "                          join words w2 on r.word_id = w2.id\n" +
            "                 where t2.id = t.id\n" +
            "                   and w2.id = w.id)\n" +
            "order by random() limit 1", nativeQuery = true)
    Long    getLastWordId(@Param("trialId") Long trialId);

    @Query(value = "select (select count(1)\n" +
            "        from trials t\n" +
            "                 join results r on t.id = r.trial_id\n" +
            "                 join words w2 on r.word_id = w2.id\n" +
            "        where t.id = :trialId) *100/ (select count(1)\n" +
            "                                  from trials t\n" +
            "                                           join collections c on t.collection_id = c.id\n" +
            "                                           join collections_world_entities cwe on c.id = cwe.collection_entity_id\n" +
            "                                           join words w on cwe.world_entities_id = w.id\n" +
            "                                  where t.id = :trialId)\n", nativeQuery = true)
    Integer getStatistic1(@Param("trialId") Long trialId);
}

