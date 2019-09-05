package ru.zagamaza.sublearn.domain.service;

import ru.zagamaza.sublearn.dto.CollectionDto;

public interface CollectionService {

    CollectionDto updateCollection(CollectionDto saveDto, CollectionDto updateDto);

}
