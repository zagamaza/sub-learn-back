package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.CollectionEntity;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {

    @Query(value = "select c.id\n" +
            "from trials\n" +
            "         join collections c on trials.collection_id = c.id\n" +
            "         join users u on trials.user_id = u.id\n" +
            "where u.id = :userId\n" +
            "group by c.id order by c.id desc"
            , countQuery = "select count(c.id)\n" +
            "from trials\n" +
            "         join collections c on trials.collection_id = c.id\n" +
            "         join users u on trials.user_id = u.id\n" +
            "where u.id = :userId\n" +
            "group by c.id order by c.id desc",
            nativeQuery = true)
    List<Long> getUniqCollectionIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}

