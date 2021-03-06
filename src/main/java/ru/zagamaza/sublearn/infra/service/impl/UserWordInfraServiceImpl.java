package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.domain.service.UserWordService;
import ru.zagamaza.sublearn.dto.UserWordDto;
import ru.zagamaza.sublearn.exception.domain.NotFoundException;
import ru.zagamaza.sublearn.infra.dao.entity.UserWordEntity;
import ru.zagamaza.sublearn.infra.dao.repository.UserWordRepository;
import ru.zagamaza.sublearn.infra.service.UserWordInfraService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserWordInfraServiceImpl implements UserWordInfraService {

    private final UserWordService userWordService;
    private final UserWordRepository repository;
    private final MessageSource messageSource;

    @Override
    public UserWordDto get(Long id) {
        UserWordEntity entity = repository.findById(id)
                                          .orElseThrow(() -> new NotFoundException(
                                                  getMessage("userWord.not.found.exception", id)
                                          ));
        return UserWordDto.from(entity);
    }

    @Override
    public List<UserWordDto> getAll(Pageable pageable) {
        Page<UserWordEntity> entities = repository.findAll(pageable);
        return entities
                .stream()
                .map(UserWordDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public UserWordDto save(UserWordDto dto) {
        UserWordEntity entity = UserWordEntity.from(dto);
        entity = repository.save(entity);
        return UserWordDto.from(entity);
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }

    @Override
    public UserWordDto save(Long wordId, Long userId, boolean isRight) {
        UserWordEntity entity = repository
                .findByWordEntityIdAndUserEntityId(wordId, userId)
                .orElse(UserWordEntity.from(new UserWordDto(userId, wordId)));
        UserWordDto userWordDto = userWordService.calculateRate(UserWordDto.from(entity), isRight);
        return save(userWordDto);
    }

    @Override
    public UserWordDto save(Long wordId, Long userId) {
        UserWordEntity entity = repository
                .findByWordEntityIdAndUserEntityId(wordId, userId)
                .orElse(UserWordEntity.from(new UserWordDto(userId, wordId)));
        entity.setRate(10);
        return save(UserWordDto.from(entity));
    }

    @Override
    @Transactional
    public void resetProgress(Long userId) {
        repository.deleteAllByUserEntityId(userId);
    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }


}
