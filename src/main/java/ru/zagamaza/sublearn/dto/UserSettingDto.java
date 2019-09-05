package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.UserSettingEntity;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingDto {

    private Long id;

    private Long userId;

    @NotNull
    private Integer wordCountInTrial;

    @NotNull
    private Integer answerOptionsCount;

    @NotNull
    private boolean isRemindAboutTrial;

    @NotNull
    private Integer thresholdLearnedPercent;

    @NotNull
    private boolean isShowAllTranslate;

    public static UserSettingDto from(UserSettingEntity entity) {
        return new UserSettingDto(
                entity.getId(),
                entity.getUserEntity().getId(),
                entity.getWordCountInTrial(),
                entity.getAnswerOptionsCount(),
                entity.isRemindAboutTrial(),
                entity.getThresholdLearnedPercent(),
                entity.isShowAllTranslate()
        );
    }

}
