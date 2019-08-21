package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.TrialEntity;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrialCondensedDto {

    private Long id;
    private String name;
    private Long userId;
    private Long collectionId;
    private String collectionName;
    private Integer percent;
    private LocalDateTime created;


    public static TrialCondensedDto from(TrialEntity entity) {
        return new TrialCondensedDto(
                entity.getId(),
                entity.getName(),
                entity.getUserEntity().getId(),
                entity.getCollectionEntity().getId(),
                entity.getCollectionEntity().getName(),
                entity.getPercent(),
                entity.getCreated()
        );
    }

    public static TrialCondensedDto from(TrialDto dto) {
        return new TrialCondensedDto(
                dto.getId(),
                dto.getName(),
                dto.getUserDto().getId(),
                dto.getCollectionDto().getId(),
                dto.getCollectionDto().getName(),
                dto.getPercent(),
                dto.getCreated()
        );
    }
}
