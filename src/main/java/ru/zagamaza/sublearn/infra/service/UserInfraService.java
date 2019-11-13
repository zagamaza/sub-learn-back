package ru.zagamaza.sublearn.infra.service;

import org.springframework.data.domain.Pageable;
import ru.zagamaza.sublearn.dto.UserDto;

import java.util.List;

public interface UserInfraService {

    UserDto get(Long id);

    List<UserDto> getAll(Pageable pageable);

    UserDto save(UserDto dto);

    void removeById(long id);

    UserDto getByUserName(String userName);

    UserDto getByTrialId(Long trialId);

    UserDto getByTelegramId(Long telegramId);

}
