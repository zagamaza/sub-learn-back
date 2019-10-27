package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.EpisodeEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeDto {

    private Long id;

    private List<WordDto> words;

    private CollectionDto collectionDto;

    private Integer season;

    private Integer episode;

    private Integer learnedPercent;

    public static EpisodeDto from(EpisodeEntity entity) {
        return new EpisodeDto(
                entity.getId(),
                isEmpty(entity.getWorldEntities())
                        ? null
                        : entity.getWorldEntities().stream()
                                .map(WordDto::from)
                                .collect(Collectors.toList()),
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
