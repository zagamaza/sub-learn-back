package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.EpisodeEntity;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeDto {

    private Long id;

    private Set<WordDto> words;

    private CollectionDto collectionDto;

    private Integer season;

    private Integer episode;

    private Integer learnedPercent;

    public static EpisodeDto compressedFrom(EpisodeEntity entity) {
        if (entity == null) {
            return null;
        }
        return EpisodeDto.builder()
                .id(entity.getId())
                .collectionDto(entity.getCollectionEntity() == null
                        ? null
                        : CollectionDto.compressedFrom(entity.getCollectionEntity()))
                .season(entity.getSeason())
                .episode(entity.getEpisode())
                .build();
    }

    public static EpisodeDto from(EpisodeEntity entity) {
        return new EpisodeDto(
                entity.getId(),
                isEmpty(entity.getWorldEntities())
                        ? null
                        : entity.getWorldEntities().stream()
                                .map(WordDto::from)
                                .collect(Collectors.toSet()),
                entity.getCollectionEntity() == null
                        ? null
                        : CollectionDto.compressedFrom(entity.getCollectionEntity()),
                entity.getSeason(),
                entity.getEpisode(),
                null
        );
    }

    public static EpisodeDto from(EpisodeRequest dto) {
        return new EpisodeDto(
                dto.getId(),
                dto.getWords(),
                CollectionDto.builder().id(dto.getCollectionId()).build(),
                dto.getSeason(),
                dto.getEpisode(),
                null
        );
    }

}
