package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.domain.exception.NotFoundException;
import ru.zagamaza.sublearn.dto.UserWordDto;
import ru.zagamaza.sublearn.infra.dao.entity.UserWordEntity;
import ru.zagamaza.sublearn.infra.dao.repository.UserWordRepository;
import ru.zagamaza.sublearn.infra.service.api.UserWordInfraService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserWordInfraServiceImpl implements UserWordInfraService {

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

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }


}
