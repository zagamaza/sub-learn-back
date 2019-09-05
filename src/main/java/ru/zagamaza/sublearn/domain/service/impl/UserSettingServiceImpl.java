package ru.zagamaza.sublearn.domain.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.domain.service.UserSettingService;
import ru.zagamaza.sublearn.dto.UserDto;
import ru.zagamaza.sublearn.dto.UserSettingDto;

@Service
public class UserSettingServiceImpl implements UserSettingService {

    @Value("${default.user.setting.wordCountInTrial}")
    private Integer defaultWordCountInTrial;

    @Value("${default.user.setting.answerOptionsCount}")
    private Integer defaultAnswerOptionsCount;

    @Value("${default.user.setting.isRemindAboutTrial}")
    private boolean defaultIsRemindAboutTrial;

    @Value("${default.user.setting.thresholdLearnedPercent}")
    private Integer thresholdLearnedPercent;

    @Value("${default.user.setting.isShowAllTranslate}")
    private boolean isShowAllTranslate;

    @Override
    public UserSettingDto updateUserSetting(UserSettingDto saveDto, UserSettingDto updateDto) {
        saveDto.setAnswerOptionsCount(updateDto.getAnswerOptionsCount());
        saveDto.setRemindAboutTrial(updateDto.isRemindAboutTrial());
        saveDto.setWordCountInTrial(updateDto.getWordCountInTrial());
        saveDto.setThresholdLearnedPercent(updateDto.getThresholdLearnedPercent());
        saveDto.setShowAllTranslate(updateDto.isShowAllTranslate());
        return saveDto;
    }

    @Override
    public UserSettingDto getDefaultUserSettingDto() {
        return new UserSettingDto(
                null,
                null,
                defaultWordCountInTrial,
                defaultAnswerOptionsCount,
                defaultIsRemindAboutTrial,
                thresholdLearnedPercent,
                isShowAllTranslate
        );
    }

    @Override
    public UserSettingDto getUserSettingByUser(UserDto userDto) {
        UserSettingDto userSettingDto = userDto.getUserSettingDto();
        if (userSettingDto == null) {
            return getDefaultUserSettingDto();
        } else {
            return userSettingDto;
        }
    }

}
