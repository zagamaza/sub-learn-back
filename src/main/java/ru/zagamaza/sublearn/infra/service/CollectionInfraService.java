package ru.zagamaza.sublearn.infra.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.zagamaza.sublearn.dto.CollectionCondensedDto;
import ru.zagamaza.sublearn.dto.CollectionDto;

import java.util.List;

public interface CollectionInfraService {

    CollectionDto get(Long id);

    List<CollectionDto> getAllByPage(Pageable pageable);

    CollectionDto update(CollectionDto dto);

    CollectionDto saveCollectionAndLinkUser(CollectionDto dto);

    CollectionDto save(CollectionDto dto);

    void removeById(long id);

    Page<CollectionCondensedDto> getCondensedCollectionByUserId(Long userId, Pageable pageable);

    CollectionDto updateIsSerial(Long id, Boolean isSerial);

    List<CollectionCondensedDto> findByContainsName(String collectionName, Pageable pageable);

    CollectionDto copyCollectionToUser(Long id, Long userId);

    void deleteLink(Long id, Long userId);

    CollectionDto getByImdbId(String imdbId);

}
