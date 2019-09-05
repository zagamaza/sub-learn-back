package ru.zagamaza.sublearn.infra.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.TranslationDto;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "translations")
public class TranslationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partSpeech;

    @ElementCollection
    private List<String> translate;

    public static TranslationEntity fom(TranslationDto translationDto) {
        return new TranslationEntity(
                translationDto.getId(),
                translationDto.getPartSpeech(),
                translationDto.getTranslate()
        );
    }

}
