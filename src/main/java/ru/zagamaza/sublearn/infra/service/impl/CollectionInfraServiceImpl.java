package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.domain.exception.NotFoundException;
import ru.zagamaza.sublearn.dto.CollectionCondensedDto;
import ru.zagamaza.sublearn.dto.CollectionDto;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.infra.dao.entity.CollectionEntity;
import ru.zagamaza.sublearn.infra.dao.repository.CollectionRepository;
import ru.zagamaza.sublearn.infra.service.api.CollectionInfraService;
import ru.zagamaza.sublearn.infra.service.api.WordInfraService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CollectionInfraServiceImpl implements CollectionInfraService {

    private final CollectionRepository repository;
    private final WordInfraService wordInfraService;
    private final MessageSource messageSource;

    @Override
    public CollectionDto get(Long id) {
        CollectionEntity entity = repository.findById(id)
                                            .orElseThrow(() -> new NotFoundException(getMessage(
                                                    "collection.not.found.exception",
                                                    id
                                            )));

        return CollectionDto.from(entity);
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
    public CollectionDto save(CollectionDto dto) {
        CollectionEntity entity;
        if (dto.getId() != null) {
            entity = repository.findById(dto.getId())
                               .stream()
                               .peek(o -> {
                                   o.setCreated(dto.getCreated());
                                   o.setLang(dto.getLang());
                               })
                               .findFirst()
                               .orElseThrow(() -> new NotFoundException(getMessage(
                                       "collection.not.found.exception",
                                       dto.getId()
                               )));
        } else {
            entity = repository.save(new CollectionEntity());
            dto.setId(entity.getId());
            dto.getWords().forEach(
                    word -> {
                        try {
                            WordDto wordDto = wordInfraService.getByName(word.getWord());
                            word.setWord(wordDto.getWord());
                            word.setId(wordDto.getId());
                        } catch (NotFoundException ignored) {
                        }
                    }
            );
            entity = repository.save(CollectionEntity.from(dto));
        }

        return CollectionDto.from(entity);
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<CollectionCondensedDto> getCondensedCollectionByUserId(Long userId, Pageable pageable) {
        List<Long> collectionIds = repository.getUniqCollectionIdsByUserId(userId, pageable);
        Collections.reverse(collectionIds);
        return collectionIds.stream()
                            .map(this::get)
                            .map(CollectionCondensedDto::from)
                            .collect(Collectors.toList());

    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }


}
