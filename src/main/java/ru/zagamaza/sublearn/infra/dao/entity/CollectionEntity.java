package ru.zagamaza.sublearn.infra.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.CollectionDto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WordEntity> worldEntities;

    private String lang;

    private String name;

    private LocalDateTime created;


    public static CollectionEntity from(CollectionDto dto) {
        return new CollectionEntity(
                dto.getId(),
                dto.getWords().stream()
                        .map(WordEntity::from)
                        .collect(Collectors.toList()),
                dto.getLang(),
                dto.getName(),
                dto.getCreated()
        );
    }

    public static CollectionEntity compressedFrom(CollectionDto dto) {
        return new CollectionEntity(
                dto.getId(),
                null,
                dto.getLang(),
                dto.getName(),
                LocalDateTime.now()
        );
    }

}
