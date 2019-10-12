package ru.zagamaza.sublearn.domain.service;

import ru.zagamaza.sublearn.dto.UserDto;
import ru.zagamaza.sublearn.dto.UserSettingDto;

public interface UserSettingService {

    UserSettingDto updateUserSetting(UserSettingDto saveDto, UserSettingDto updateDto);

    UserSettingDto getDefaultUserSettingDto();

    UserSettingDto getUserSettingByUser(UserDto userDto);

}
