package ru.zagamaza.sublearn.infra.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.UserSettingDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_settings", indexes = @Index(name = "user_settings_user_id_ix", columnList = "user_id"))
public class UserSettingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Column(name = "word_count_in_trial", columnDefinition = "integer default 20")
    private Integer wordCountInTrial;

    @Column(name = "answer_options_count", columnDefinition = "integer default 4")
    private Integer answerOptionsCount;

    @Column(name = "is_remind_about_trial", columnDefinition = "boolean default false")
    private boolean isRemindAboutTrial;

    @Column(name = "threshold_learned_percent")
    private Integer thresholdLearnedPercent;

    @Column(name = "is_show_all_translate")
    private boolean isShowAllTranslate;

    @Column(name = "learned_word_count", columnDefinition = "integer default 3")
    private Integer learnedWordCount;


    public static UserSettingEntity from(UserSettingDto dto) {
        return new UserSettingEntity(
                dto.getId(),
                UserEntity.builder().id(dto.getUserId()).build(),
                dto.getWordCountInTrial(),
                dto.getAnswerOptionsCount(),
                dto.isRemindAboutTrial(),
                dto.getThresholdLearnedPercent(),
                dto.isShowAllTranslate(),
                dto.getLearnedWordCount()
        );
    }

}
