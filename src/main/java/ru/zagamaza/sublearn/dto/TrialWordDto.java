package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.TrialWordEntity;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrialWordDto {

    private Long id;

    private WordDto wordDto;

    private TrialDto trialDto;

    private boolean isRight;

    private boolean isPassed;

    private boolean isLastWord;

    private LocalDateTime created;

    public static TrialWordDto from(TrialWordEntity entity) {
        return new TrialWordDto(
                entity.getId(),
                WordDto.from(entity.getWordEntity()),
                TrialDto.compressedFrom(entity.getTrialEntity()),
                entity.isRight(),
                entity.isPassed(),
                entity.isLastWord(),
                entity.getCreated()
        );
    }

    public static TrialWordDto from(TrialWordRequest trialWordRequest) {
        return new TrialWordDto(
                trialWordRequest.getId(),
                new WordDto(trialWordRequest.getWordId()),
                new TrialDto(trialWordRequest.getTrialId()),
                trialWordRequest.isRight(),
                trialWordRequest.isPassed(),
                false,
                trialWordRequest.getCreated()
        );
    }

}
