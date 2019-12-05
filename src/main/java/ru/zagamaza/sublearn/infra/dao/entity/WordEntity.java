package ru.zagamaza.sublearn.infra.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.WordDto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "words", indexes = @Index(name = "name_seq", columnList = "word"))
public class WordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    private String transcription;
    private String mainTranslation;
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "word_id")
    private List<TranslationEntity> translationEntities;
    private String lang;
    private LocalDateTime created;

    public static WordEntity from(WordDto wordDto) {
        return new WordEntity(
                wordDto.getId(),
                wordDto.getWord(),
                wordDto.getTranscription(),
                wordDto.getMainTranslation(),
                isEmpty(wordDto.getTranslation())
                        ? null
                        : wordDto.getTranslation()
                                 .stream()
                                 .map(TranslationEntity::from)
                                 .collect(Collectors.toList()),
                wordDto.getLang(),
                LocalDateTime.now()
        );
    }

}
