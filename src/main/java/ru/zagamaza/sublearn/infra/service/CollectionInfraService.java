package ru.zagamaza.sublearn.infra.service;

import org.springframework.data.domain.Pageable;
import ru.zagamaza.sublearn.dto.CollectionCondensedDto;
import ru.zagamaza.sublearn.dto.CollectionDto;

import java.util.List;

public interface CollectionInfraService {

    CollectionDto get(Long id);

    List<CollectionDto> getAllByPage(Pageable pageable);

    CollectionDto update(CollectionDto dto);

    CollectionDto save(CollectionDto dto);

    void removeById(long id);

    List<CollectionCondensedDto> getCondensedCollectionByUserId(Long userId, Pageable pageable);

    CollectionDto updateIsSerial(Long id, Boolean isSerial);

    List<CollectionCondensedDto> findByContainsName(String collectionName, Pageable pageable);

}
