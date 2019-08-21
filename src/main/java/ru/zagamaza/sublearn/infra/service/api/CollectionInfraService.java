package ru.zagamaza.sublearn.infra.service.api;

import org.springframework.data.domain.Pageable;
import ru.zagamaza.sublearn.dto.CollectionCondensedDto;
import ru.zagamaza.sublearn.dto.CollectionDto;

import java.util.List;

public interface CollectionInfraService {

    CollectionDto get(Long id);

    List<CollectionDto> getAllByPage(Pageable pageable);

    CollectionDto save(CollectionDto dto);

    void removeById(long id);

    List<CollectionCondensedDto> getCondensedCollectionByUserId(Long userId, Pageable pageable);
}
