package ru.zagamaza.sublearn.infra.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.WordDto;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "words", indexes = @Index(name = "name_seq", columnList = "word"))
public class WordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    private String transcription;
    @ElementCollection
    private List<String> translation;
    private String lang;
    private LocalDateTime created;


    public static WordEntity from(WordDto wordDto) {
        return new WordEntity(
                wordDto.getId(),
                wordDto.getWord(),
                wordDto.getTranscription(),
                wordDto.getTranslation(),
                wordDto.getLang(),
                wordDto.getCreated()
        );
    }

}
