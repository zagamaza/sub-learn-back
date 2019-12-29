package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.CollectionEntity;
import ru.zagamaza.sublearn.infra.dao.entity.Lang;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDto {

    private Long id;

    private Long imdbId;

    private List<EpisodeDto> episodeDtos;

    private Long userId;

    @NotNull
    private Lang lang;

    @NotNull
    private String name;

    private String url;

    private Integer rating;

    private boolean isShared;

    private boolean isSerial;

    private LocalDateTime created;


    public static CollectionDto compressedFrom(CollectionEntity entity) {
        return CollectionDto.builder()
                            .id(entity.getId())
                            .imdbId(entity.getImdbId())
                            .lang(entity.getLang())
                            .name(entity.getName())
                            .url(entity.getUrl())
                            .rating(entity.getRating())
                            .isShared(entity.isShared())
                            .isSerial(entity.isSerial())
                            .created(entity.getCreated())
                            .build();
    }

    public static CollectionDto from(CollectionEntity entity) {
        if (entity == null) {
            return null;
        }
        return new CollectionDto(
                entity.getId(),
                entity.getImdbId(),
                isEmpty(entity.getEpisodeEntities())
                        ? null
                        : entity.getEpisodeEntities()
                                .stream()
                                .map(EpisodeDto::from)
                                .collect(Collectors.toList()),
                null,
                entity.getLang(),
                entity.getName(),
                entity.getUrl(),
                entity.getRating(),
                entity.isShared(),
                entity.isSerial(),
                entity.getCreated()
        );
    }

    public static CollectionDto from(CollectionRequest request) {
        return new CollectionDto(
                request.getId(),
                request.getImdbId(),
                null,
                request.getUserId(),
                request.getLang(),
                request.getName(),
                request.getUrl(),
                request.getRating(),
                request.isShared(),
                request.isSerial(),
                request.getCreated()
        );
    }

}
