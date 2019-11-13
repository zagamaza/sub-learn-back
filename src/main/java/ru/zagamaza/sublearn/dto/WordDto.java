package ru.zagamaza.sublearn.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.WordEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordDto {

    private Long id;
    private String word;
    private String transcription;
    private List<TranslationDto> translation;
    private String lang;
    private LocalDateTime created;

    public WordDto(Long id) {
        this.id = id;
    }

    public static WordDto from(WordEntity entity) {
        return new WordDto(
                entity.getId(),
                entity.getWord(),
                entity.getTranscription(),
                isEmpty(entity.getTranslationEntities())
                        ? null
                        : entity.getTranslationEntities()
                                .stream()
                                .map(TranslationDto::from)
                                .collect(Collectors.toList()),
                entity.getLang(),
                entity.getCreated()
        );
    }

}
