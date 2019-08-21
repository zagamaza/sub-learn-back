package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.CollectionEntity;

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
    private List<WordDto> words;

    @NotNull
    private String lang;

    @NotNull
    private String name;
    private LocalDateTime created;


    public static CollectionDto compressedFrom(CollectionEntity entity) {
        return CollectionDto.builder()
                            .id(entity.getId())
                            .created(entity.getCreated())
                            .name(entity.getName())
                            .lang(entity.getLang())
                            .build();
    }

    public static CollectionDto from(CollectionEntity entity) {
        return new CollectionDto(
                entity.getId(),
                isEmpty(entity.getWorldEntities())
                        ? null
                        : entity.getWorldEntities().stream()
                                .map(WordDto::from)
                                .collect(Collectors.toList())
                ,
                entity.getLang(),
                entity.getName(),
                entity.getCreated()
        );
    }

}
