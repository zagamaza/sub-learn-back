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

    private List<EpisodeDto> episodeDtos;

    private UserDto userDto;

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
                            .created(entity.getCreated())
                            .name(entity.getName())
                            .lang(entity.getLang())
                            .isSerial(entity.isSerial())
                            .build();
    }

    public static CollectionDto from(CollectionEntity entity) {
        return new CollectionDto(
                entity.getId(),
                isEmpty(entity.getEpisodeEntities())
                        ? null
                        : entity.getEpisodeEntities()
                                .stream()
                                .map(EpisodeDto::from)
                                .collect(Collectors.toList()),
                UserDto.from(entity.getUserEntity()),
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
                null,
                UserDto.builder().id(request.getUserId()).build(),
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
