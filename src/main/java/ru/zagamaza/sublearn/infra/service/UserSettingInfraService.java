package ru.zagamaza.sublearn.infra.service;

import ru.zagamaza.sublearn.dto.UserSettingDto;
import ru.zagamaza.sublearn.dto.WordDto;

public interface UserSettingInfraService {

    UserSettingDto get(Long id);

    UserSettingDto update (UserSettingDto dto);

    UserSettingDto save(UserSettingDto dto);

    void removeById(long id);

    UserSettingDto getByTrialId(Long trialId);

    UserSettingDto getByUserId(Long id);

}
