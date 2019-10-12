package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.domain.service.UserSettingService;
import ru.zagamaza.sublearn.dto.UserDto;
import ru.zagamaza.sublearn.dto.UserSettingDto;
import ru.zagamaza.sublearn.exception.domain.NotFoundException;
import ru.zagamaza.sublearn.infra.dao.entity.UserSettingEntity;
import ru.zagamaza.sublearn.infra.dao.repository.UserSettingRepository;
import ru.zagamaza.sublearn.infra.service.UserInfraService;
import ru.zagamaza.sublearn.infra.service.UserSettingInfraService;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserSettingInfraServiceImpl implements UserSettingInfraService {

    private final UserSettingRepository repository;
    private final UserSettingService userSettingService;
    private final UserInfraService userInfraService;
    private final MessageSource messageSource;

    @Override
    public UserSettingDto get(Long id) {
        UserSettingEntity entity = repository.findById(id)
                                             .orElseThrow(() -> new NotFoundException(
                                                     getMessage("userSetting.not.found.exception", id)
                                             ));
        return UserSettingDto.from(entity);
    }

    @Override
    public UserSettingDto update(UserSettingDto updateDto) {
        UserSettingDto saveDto = get(updateDto.getId());
        userSettingService.updateUserSetting(saveDto, updateDto);
        return save(saveDto);
    }

    @Override
    public UserSettingDto save(UserSettingDto dto) {
        UserSettingEntity userSettingEntity = repository.save(UserSettingEntity.from(dto));
        return UserSettingDto.from(userSettingEntity);
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }

    @Override
    public UserSettingDto getByTrialId(Long trialId){
        UserDto userDto = userInfraService.getByTrialId(trialId);
        return userSettingService.getUserSettingByUser(userDto);
    }


    @Override
    public UserSettingDto getByUserId(Long userId) {
        UserSettingEntity entity = repository.findByUserEntityId(userId)
                                             .orElse(UserSettingEntity.from(
                                                     userSettingService.getDefaultUserSettingDto())
                                             );
        return UserSettingDto.from(entity);

    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
