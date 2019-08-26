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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "collections")
public class CollectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "collectionEntity")
    private List<EpisodeEntity> episodeEntities;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Enumerated(value = EnumType.STRING)
    private Lang lang;

    private String name;

    @Column(name = "is_serial")
    private boolean isSerial;

    private LocalDateTime created;


    public static CollectionEntity from(CollectionDto dto) {
        return new CollectionEntity(
                dto.getId(),
                isEmpty(dto.getEpisodeDtos())
                        ? null
                        : dto.getEpisodeDtos()
                             .stream()
                             .map(EpisodeEntity::from)
                             .collect(Collectors.toList()),
                UserEntity.from(dto.getUserDto()),
                dto.getLang(),
                dto.getName(),
                dto.isSerial(),
                dto.getCreated()
        );
    }

    public static CollectionEntity compressedFrom(CollectionDto dto) {
        return new CollectionEntity(
                dto.getId(),
                dto.getEpisodeDtos()
                   .stream()
                   .map(EpisodeEntity::from)
                   .collect(Collectors.toList()),
                UserEntity.from(dto.getUserDto()),
                dto.getLang(),
                dto.getName(),
                dto.isSerial(),
                LocalDateTime.now()
        );
    }

}
