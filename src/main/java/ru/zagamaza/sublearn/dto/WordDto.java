package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.WordEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WordDto {

    private Long id;
    private String word;
    private String transcription;
    private List<String> translation;
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
                entity.getTranslation(),
                entity.getLang(),
                entity.getCreated()
        );
    }

}
