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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "episodes", indexes = @Index(name = "episodes_collection_id_ix", columnList = "collection_id"))
public class EpisodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "episodes_world_entities",
               joinColumns = @JoinColumn(name = "episode_entity_id"),
               inverseJoinColumns = @JoinColumn(name = "world_entities_id"),
               indexes = {
                       @Index(name = "episode_entity_id_ix", columnList = "episode_entity_id"),
                       @Index(name = "world_entities_id_ix", columnList = "world_entities_id")
               }
    )
    private Set<WordEntity> worldEntities;

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
                             .collect(Collectors.toSet()),
                CollectionEntity.builder().id(dto.getCollectionDto().getId()).build(),
                dto.getSeason(),
                dto.getEpisode()
        );
    }

}
