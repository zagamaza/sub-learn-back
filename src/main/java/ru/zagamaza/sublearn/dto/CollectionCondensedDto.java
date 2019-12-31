package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.CollectionEntity;
import ru.zagamaza.sublearn.infra.dao.entity.Lang;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CollectionCondensedDto {

    private Long id;
    private String imdbId;
    private Lang lang;
    private String name;
    private String url;
    private Integer rating;
    private boolean isShared;
    private Boolean isSerial;
    private LocalDateTime created;


    public static CollectionCondensedDto from(CollectionDto collectionDto) {
        return new CollectionCondensedDto(
                collectionDto.getId(),
                collectionDto.getImdbId(),
                collectionDto.getLang(),
                collectionDto.getName(),
                collectionDto.getUrl(),
                collectionDto.getRating(),
                collectionDto.isShared(),
                collectionDto.isSerial(),
                collectionDto.getCreated()
        );
    }

    public static CollectionCondensedDto from(CollectionEntity entity) {
        return new CollectionCondensedDto(
                entity.getId(),
                entity.getImdbId(),
                entity.getLang(),
                entity.getName(),
                entity.getUrl(),
                entity.getRating(),
                entity.isShared(),
                entity.isSerial(),
                entity.getCreated()
        );
    }

}
