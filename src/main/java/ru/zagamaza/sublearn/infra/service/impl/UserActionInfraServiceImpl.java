package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.dto.UserActionDto;
import ru.zagamaza.sublearn.exception.domain.NotFoundException;
import ru.zagamaza.sublearn.infra.dao.entity.UserActionEntity;
import ru.zagamaza.sublearn.infra.dao.repository.UserActionRepository;
import ru.zagamaza.sublearn.infra.service.UserActionInfraService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserActionInfraServiceImpl implements UserActionInfraService {

    private final UserActionRepository repository;
    private final MessageSource messageSource;

    @Override
    public Page<UserActionDto> getAll(Pageable pageable) {
        Page<UserActionEntity> userActionEntities = repository.findAll(pageable);
        List<UserActionDto> userActionDtos = userActionEntities
                .stream()
                .map(UserActionDto::from)
                .collect(Collectors.toList());
        return new PageImpl<>(userActionDtos, pageable, userActionEntities.getTotalElements());
    }

    @Override
    public UserActionDto get(Long id) {
        UserActionEntity entity = repository.findById(id)
                                            .orElseThrow(() -> new NotFoundException(getMessage(
                                                    "userAction.not.found.exception", id
                                            )));
        return UserActionDto.from(entity);
    }

    @Override
    public UserActionDto save(UserActionDto dto) {
        UserActionEntity userActionEntity = UserActionEntity.from(dto);
        userActionEntity = repository.save(userActionEntity);
        return UserActionDto.from(userActionEntity);
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<UserActionDto> getByUserId(Long userId, Pageable pageable) {
        Page<UserActionEntity> userActionEntities = repository.findAllByUserEntityId(userId, pageable);
        List<UserActionDto> userActionDtos = userActionEntities.stream()
                                                               .map(UserActionDto::from)
                                                               .collect(Collectors.toList());
        return new PageImpl<>(userActionDtos, pageable, userActionEntities.getTotalElements());
    }


    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
