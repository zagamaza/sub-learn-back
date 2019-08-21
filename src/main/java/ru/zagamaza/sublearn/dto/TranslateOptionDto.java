package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranslateOptionDto {

    private TrialCondensedDto trialCondensedDto;
    private WordDto translatable;
    private List<WordDto> translations;
    private Integer present;
    private Integer correctPercent;

    public static TranslateOptionDto from(TrialDto trialDto) {

        return new TranslateOptionDto(
                TrialCondensedDto.from(trialDto),
                null,
                null,
                trialDto.getPercent(),
                trialDto.getCorrectPercent()
        );
    }

}
