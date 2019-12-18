package ru.zagamaza.sublearn.infra.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.zagamaza.sublearn.dto.UserActionDto;

public interface UserActionInfraService {

    Page<UserActionDto> getAll(Pageable pageable);

    UserActionDto get(Long id);

    UserActionDto save(UserActionDto dto);

    void removeById(long id);

    Page<UserActionDto> getByUserId(Long userId, Pageable pageable);

}
