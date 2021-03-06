package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.domain.service.CollectionService;
import ru.zagamaza.sublearn.dto.CollectionCondensedDto;
import ru.zagamaza.sublearn.dto.CollectionDto;
import ru.zagamaza.sublearn.exception.domain.NotFoundException;
import ru.zagamaza.sublearn.infra.dao.entity.CollectionEntity;
import ru.zagamaza.sublearn.infra.dao.repository.CollectionRepository;
import ru.zagamaza.sublearn.infra.service.CollectionInfraService;

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
    @Transactional
    public CollectionDto saveCollectionAndLinkUser(CollectionDto dto) {
        CollectionEntity entity = repository.save(CollectionEntity.from(dto));
        repository.saveLinkUserToCollection(entity.getId(), dto.getUserId());
        return CollectionDto.from(entity);
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
    public Page<CollectionCondensedDto> getCondensedCollectionByUserId(Long userId, Pageable pageable) {
        return new PageImpl<>(
                repository.findAllByUserEntityId(userId, pageable)
                          .stream()
                          .map(CollectionCondensedDto::from)
                          .collect(Collectors.toList()),
                pageable,
                repository.countByUserEntityId(userId)
        );
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
    @Transactional
    public CollectionDto copyCollectionToUser(Long id, Long userId) {
        repository.increaseRating(id);
        repository.saveLinkUserToCollection(id, userId);
        return get(id);
    }

    @Override
    @Transactional
    public void deleteLink(Long id, Long userId) {
        repository.deleteLinkUserToCollection(id, userId);
    }

    @Override
    public CollectionDto getByImdbId(String imdbId) {
        CollectionEntity entity = repository.findByImdbId(imdbId);
        return CollectionDto.compressedFrom(entity);
    }

    @Override
    public List<CollectionCondensedDto> findNotFinishedSerials() {
        return repository
                .findAllByIsSerialTrueAndFinishedFalse()
                .stream()
                .map(CollectionCondensedDto::from)
                .collect(Collectors.toList());
    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
