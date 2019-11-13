package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.zagamaza.sublearn.infra.dao.entity.TranslationEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class TranslationDto {

    private Long id;
    private String partSpeech;
    private List<String> translate;

    public static TranslationDto from(TranslationEntity entity) {
        return new TranslationDto(
                entity.getId(),
                entity.getPartSpeech(),
                entity.getTranslate()
        );
    }

}
