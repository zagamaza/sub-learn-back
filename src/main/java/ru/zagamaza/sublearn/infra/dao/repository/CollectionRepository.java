package ru.zagamaza.sublearn.infra.dao.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zagamaza.sublearn.infra.dao.entity.CollectionEntity;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {

    CollectionEntity findByImdbId(String imdbId);

    List<CollectionEntity> findAllByIsSerialIsTrueAndIsFinishedIsFalse();

    @Query(value = "select u.collectionEntities from UserEntity u where u.id = :userId")
    List<CollectionEntity> findAllByUserEntityId(Long userId, Pageable pageable);

    List<CollectionEntity> findAllByNameContainingIgnoreCaseAndUrlIsNotNullAndIsSharedIsTrueOrderByRatingDesc(
            String name,
            Pageable pageable
    );

    @Query(value = "insert into user_to_collection (user_id, collection_id) values (:userId, :collectionId)",
           nativeQuery = true)
    @Modifying
    void saveLinkUserToCollection(@Param("collectionId") Long collectionId, @Param("userId") Long userId);

    @Query(value = "delete from user_to_collection utc where utc.user_id = :userId and utc.collection_id = :collectionId",
           nativeQuery = true)
    @Modifying
    void deleteLinkUserToCollection(@Param("collectionId") Long collectionId, @Param("userId") Long userId);

    @Query(value = "select count(1) from collections c \n" +
            " join user_to_collection utc on c.id = utc.collection_id\n" +
            " where utc.user_id = :userId", nativeQuery = true)
    Integer countByUserEntityId(@Param("userId") Long userId);

    @Query(value = "update collections c\n" +
            "set rating = (select cs.rating from collections cs where cs.id = :id) + 1\n" +
            "where c.id = :id",
           nativeQuery = true)
    @Modifying
    void increaseRating(@Param("id") Long id);

}

