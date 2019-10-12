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
    private Lang lang;
    private String name;
    private Boolean isSerial;
    private LocalDateTime created;


    public static CollectionCondensedDto from(CollectionDto collectionDto) {
        return new CollectionCondensedDto(
                collectionDto.getId(),
                collectionDto.getLang(),
                collectionDto.getName(),
                collectionDto.isSerial(),
                collectionDto.getCreated()
        );
    }

    public static CollectionCondensedDto from(CollectionEntity entity) {
        return new CollectionCondensedDto(
                entity.getId(),
                entity.getLang(),
                entity.getName(),
                entity.isSerial(),
                entity.getCreated()
        );
    }

}
