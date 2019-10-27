package ru.zagamaza.sublearn.infra.service;


import org.springframework.data.domain.Pageable;
import ru.zagamaza.sublearn.dto.UserWordDto;

import java.util.List;

public interface UserWordInfraService {

    UserWordDto get(Long id);

    List<UserWordDto> getAll(Pageable pageable);

    UserWordDto save(UserWordDto dto);

    void removeById(long id);

    UserWordDto save(Long wordId, Long userId, boolean isRight);

    UserWordDto save(Long wordId, Long userId);

}
