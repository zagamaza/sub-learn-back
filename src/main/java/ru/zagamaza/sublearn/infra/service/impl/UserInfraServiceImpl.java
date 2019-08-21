package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.domain.exception.NotFoundException;
import ru.zagamaza.sublearn.dto.UserDto;
import ru.zagamaza.sublearn.infra.dao.entity.UserEntity;
import ru.zagamaza.sublearn.infra.dao.repository.UserRepository;
import ru.zagamaza.sublearn.infra.service.api.UserInfraService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInfraServiceImpl implements UserInfraService {

    private final UserRepository repository;
    private final MessageSource messageSource;


    @Override
    public UserDto get(Long id) {
        UserEntity entity = repository.findById(id)
                                      .orElseThrow(() -> new NotFoundException(getMessage(
                                              "user.not.found.exception",
                                              id
                                      )));
        return UserDto.from(entity);
    }

    @Override
    public List<UserDto> getAll(Pageable pageable) {
        Page<UserEntity> entities = repository.findAll(pageable);
        return entities
                .stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto save(UserDto dto) {
        UserEntity entity = repository.save(UserEntity.from(dto));
        return UserDto.from(entity);
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
