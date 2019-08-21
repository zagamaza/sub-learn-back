package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.ResultEntity;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {

    private Long id;

    private WordDto wordDto;

    private TrialDto trialDto;

    boolean isRight;

    private LocalDateTime created;

    public static ResultDto from(ResultEntity entity) {
        return new ResultDto(
                entity.getId(),
                WordDto.from(entity.getWordEntity()),
                TrialDto.compressedFrom(entity.getTrialEntity()),
                entity.isRight(),
                entity.getCreated()
        );
    }

    public static ResultDto from(ResultRequestDto resultRequestDto) {
        return new ResultDto(
                resultRequestDto.getId(),
                new WordDto(resultRequestDto.getWordId()),
                new TrialDto(resultRequestDto.getTrialId()),
                resultRequestDto.isRight(),
                resultRequestDto.getCreated()
        );
    }
}
