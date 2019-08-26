package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.domain.exception.NotFoundException;
import ru.zagamaza.sublearn.dto.EpisodeDto;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.infra.dao.entity.CollectionEntity;
import ru.zagamaza.sublearn.infra.dao.entity.EpisodeEntity;
import ru.zagamaza.sublearn.infra.dao.repository.EpisodeRepository;
import ru.zagamaza.sublearn.infra.service.api.EpisodeInfraService;
import ru.zagamaza.sublearn.infra.service.api.WordInfraService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EpisodeInfraServiceImpl implements EpisodeInfraService {

    private final EpisodeRepository repository;
    private final MessageSource messageSource;
    private final WordInfraService wordInfraService;

    @Override
    public EpisodeDto get(Long id) {
        EpisodeEntity entity = repository.findById(id)
                                         .orElseThrow(() -> new NotFoundException(
                                                 getMessage("episode.not.found.exception", id)
                                         ));
        return EpisodeDto.from(entity);
    }

    @Override
    public List<EpisodeDto> getAll(Pageable pageable) {
        Page<EpisodeEntity> entities = repository.findAll(pageable);
        return entities
                .stream()
                .map(EpisodeDto::from)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public EpisodeDto update(EpisodeDto dto) {
        repository.findById(dto.getId()).orElseThrow(() -> new NotFoundException(getMessage(
                "episode.not.found.exception",
                dto.getId()
        )));
        EpisodeEntity entity = EpisodeEntity.from(dto);
        entity = repository.save(entity);
        return EpisodeDto.from(entity);
    }

    @Override
    @Transactional
    public EpisodeDto save(EpisodeDto dto) {
        EpisodeEntity entity = EpisodeEntity.from(dto);
        entity.setId(repository.save(new EpisodeEntity()).getId());
        dto.setId(entity.getId());
        dto.getWords().forEach(
                word -> {
                    try {
                        WordDto wordDto = wordInfraService.getByName(word.getWord());
                        word.setWord(wordDto.getWord());
                        word.setId(wordDto.getId());
                    } catch (NotFoundException ignored) {}

                });
        entity = repository.save(EpisodeEntity.from(dto));
        return EpisodeDto.from(entity);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        repository.deleteById(id);
    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
