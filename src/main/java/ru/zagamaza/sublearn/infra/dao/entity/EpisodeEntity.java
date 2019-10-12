package ru.zagamaza.sublearn.infra.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.EpisodeDto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "episodes")
public class EpisodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<WordEntity> worldEntities;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "collection_id")
    private CollectionEntity collectionEntity;

    private Integer season;

    private Integer episode;

    public static EpisodeEntity from(EpisodeDto dto) {
        return new EpisodeEntity(
                dto.getId(),
                dto.getWords() == null
                        ? null
                        : dto.getWords().stream()
                             .map(WordEntity::from)
                             .collect(Collectors.toList()),
                CollectionEntity.builder().id(dto.getCollectionDto().getId()).build(),
                dto.getSeason(),
                dto.getEpisode()
        );
    }

}
