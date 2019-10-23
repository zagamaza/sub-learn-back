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

    List<TrialEntity> findAllByOrderByCreatedDesc(Pageable pageable);

    @Query(value = "select count(t.id) from trials t \n" +
            "join episodes e on t.episode_id = e.id\n" +
            "join collections c on e.collection_id = c.id\n" +
            "join user_to_collection utc on c.id = utc.collection_id\n" +
            "join users u on utc.user_id = u.id\n" +
            "where u.id = :userId",
           nativeQuery = true)
    Integer countByUserId(@Param("userId") Long userId);

    @Query(value = "select t.id from trials t \n" +
            "join episodes e on t.episode_id = e.id\n" +
            "join collections c on e.collection_id = c.id\n" +
            "join user_to_collection utc on c.id = utc.collection_id\n" +
            "join users u on utc.user_id = u.id\n" +
            "where u.id = :userId",
           countQuery = "select count(1) from trials t \n" +
                   "join episodes e on t.episode_id = e.id\n" +
                   "join collections c on e.collection_id = c.id\n" +
                   "join user_to_collection utc on c.id = utc.collection_id\n" +
                   "join users u on utc.user_id = u.id\n" +
                   "where u.id = :userId",
           nativeQuery = true)
    List<Long> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "select (select count(1) from trial_word o where o.trial_id = :id and o.is_right is true) * 100 /\n" +
            "       (case when (select count(1) from trial_word o where o.trial_id = :id)!= 0\n" +
            "             then (select count(1) from trial_word o where o.trial_id = :id)\n" +
            "             else 1 end )", nativeQuery = true)
    Integer getCorrectPercent(@Param("id") Long id);

    @Query(value = "select concat((select c.name from collections c where c.id = e.collection_id),\n" +
            "              (case when e.season = 0 then '' else concat(' season-', e.season, '/series-', e.episode) end))\n" +
            "from episodes e\n" +
            "where e.id = (select episode_id from trials t where t.id = :id)", nativeQuery = true)
    String getTrialName(@Param("id") Long id);

    @Query(value = ("select (select count(1) from trial_word tw\n" +
            "        where tw.trial_id = :id and tw.is_passed is true) *  100/\n" +
            "       (case when (select count(1) from trial_word tw\n" +
            "                   where tw.trial_id = :id)!=0\n" +
            "             then (select count(1) from trial_word tw\n" +
            "                   where tw.trial_id = :id) else 1 end)"), nativeQuery = true)
    Integer getPercent(@Param("id") Long id);

}

