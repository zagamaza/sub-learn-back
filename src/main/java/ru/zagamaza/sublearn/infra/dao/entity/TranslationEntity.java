package ru.zagamaza.sublearn.infra.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.TranslationDto;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "translations", indexes = @Index(name = "trials_word_id_ix", columnList = "word_id"))
public class TranslationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partSpeech;

    @ElementCollection
    private List<String> translate;

    public static TranslationEntity from(TranslationDto translationDto) {
        return new TranslationEntity(
                translationDto.getId(),
                translationDto.getPartSpeech(),
                translationDto.getTranslate()
        );
    }

}
