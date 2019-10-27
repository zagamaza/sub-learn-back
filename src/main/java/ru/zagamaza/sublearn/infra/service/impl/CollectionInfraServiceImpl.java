package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.domain.service.CollectionService;
import ru.zagamaza.sublearn.dto.CollectionCondensedDto;
import ru.zagamaza.sublearn.dto.CollectionDto;
import ru.zagamaza.sublearn.exception.domain.NotFoundException;
import ru.zagamaza.sublearn.infra.dao.entity.CollectionEntity;
import ru.zagamaza.sublearn.infra.dao.repository.CollectionRepository;
import ru.zagamaza.sublearn.infra.service.CollectionInfraService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollectionInfraServiceImpl implements CollectionInfraService {

    private final CollectionRepository repository;
    private final CollectionService collectionService;
    private final MessageSource messageSource;

    @Override
    public CollectionDto get(Long id) {
        CollectionEntity entity = repository.findById(id)
                                            .orElseThrow(() -> new NotFoundException(getMessage(
                                                    "collection.not.found.exception", id
                                            )));
        return CollectionDto.compressedFrom(entity);
    }

    @Override
    public List<CollectionDto> getAllByPage(Pageable pageable) {
        Page<CollectionEntity> entities = repository.findAll(pageable);
        return entities
                .stream()
                .map(CollectionDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public CollectionDto update(CollectionDto updateDto) {
        CollectionDto saveDto = get(updateDto.getId());
        collectionService.updateCollection(saveDto, updateDto);
        return save(saveDto);

    }

    @Override
    public CollectionDto save(CollectionDto dto) {
        CollectionEntity entity = repository.save(CollectionEntity.from(dto));
        return CollectionDto.from(entity);
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<CollectionCondensedDto> getCondensedCollectionByUserId(Long userId, Pageable pageable) {
        return repository.findAllByUserEntityId(userId, pageable)
                         .stream()
                         .map(CollectionCondensedDto::from)
                         .collect(Collectors.toList());
    }

    @Override
    public CollectionDto updateIsSerial(Long id, Boolean isSerial) {
        CollectionDto collectionDto = get(id);
        collectionDto.setSerial(isSerial);
        return save(collectionDto);
    }

    @Override
    public List<CollectionCondensedDto> findByContainsName(String collectionName, Pageable pageable) {
        return repository
                .findAllByNameContainingIgnoreCaseAndUrlIsNotNullAndIsSharedIsTrueOrderByRatingDesc(
                        collectionName,
                        pageable
                )
                .stream()
                .map(CollectionCondensedDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public CollectionDto copyCollectionToUser(Long id, Long userId) {
        CollectionEntity entity = repository.findById(id)
                                            .orElseThrow(() -> new NotFoundException(getMessage(
                                                    "collection.not.found.exception", id
                                            )));
        entity.setRating(entity.getRating() + 1);
        repository.save(entity);
        CollectionEntity collectionEntity = new CollectionEntity();
        BeanUtils.copyProperties(entity, collectionEntity);
        collectionEntity.setEpisodeEntities(new ArrayList<>(entity.getEpisodeEntities()));
        collectionEntity.setId(null);
        collectionEntity.setShared(false);
        collectionEntity = repository.save(collectionEntity);
        return CollectionDto.from(collectionEntity);
    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
