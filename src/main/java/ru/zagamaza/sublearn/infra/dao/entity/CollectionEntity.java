package ru.zagamaza.sublearn.infra.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.CollectionDto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
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
@Table(name = "collections", indexes = @Index(name = "collections_imdb_id_ix", columnList = "imdb_id"))
public class CollectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imdb_id", unique = true)
    private String imdbId;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "collectionEntity")
    private List<EpisodeEntity> episodeEntities;

    @Enumerated(value = EnumType.STRING)
    private Lang lang;

    private String name;

    private String url;

    private Integer rating;

    @Column(name = "is_shared")
    private boolean isShared;

    @Column(name = "is_serial")
    private boolean isSerial;

    @Column(name = "is_finished", columnDefinition = "boolean default true")
    private boolean isFinished;

    private LocalDateTime created;


    public static CollectionEntity from(CollectionDto dto) {
        return new CollectionEntity(
                dto.getId(),
                dto.getImdbId(),
                isEmpty(dto.getEpisodeDtos())
                        ? null
                        : dto.getEpisodeDtos()
                             .stream()
                             .map(EpisodeEntity::from)
                             .collect(Collectors.toList()),
                dto.getLang(),
                dto.getName(),
                dto.getUrl(),
                dto.getRating(),
                dto.isShared(),
                dto.isSerial(),
                dto.isFinished(),
                dto.getCreated()
        );
    }

    public static CollectionEntity compressedFrom(CollectionDto dto) {
        return new CollectionEntity(
                dto.getId(),
                dto.getImdbId(),
                dto.getEpisodeDtos()
                   .stream()
                   .map(EpisodeEntity::from)
                   .collect(Collectors.toList()),
                dto.getLang(),
                dto.getName(),
                dto.getUrl(),
                dto.getRating(),
                dto.isShared(),
                dto.isSerial(),
                dto.isFinished(),
                LocalDateTime.now()
        );
    }

}
