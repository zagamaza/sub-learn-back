package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.domain.exception.NotFoundException;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.infra.dao.entity.WordEntity;
import ru.zagamaza.sublearn.infra.dao.repository.WorldRepository;
import ru.zagamaza.sublearn.infra.service.api.WordInfraService;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class WordInfraServiceImpl implements WordInfraService {

    private final WorldRepository repository;
    private final MessageSource messageSource;

    @Override
    public WordDto get(Long id) {
        WordEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(getMessage("word.not.found.exception", id)));
        return WordDto.from(entity);
    }

    @Override
    @Transactional
    public WordDto save(WordDto dto) {
        WordEntity wordEntity = repository.findByWord(dto.getWord()).orElse(WordEntity.from(dto));
        wordEntity = repository.save(wordEntity);
        return WordDto.from(wordEntity);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        repository.deleteById(id);
    }

    @Override
    public WordDto getByName(String name) {
        WordEntity entity = repository
                .findByWord(name)
                .orElseThrow(() -> new NotFoundException(getMessage("word.not.found.exception", name)));

        return WordDto.from(entity);
    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
