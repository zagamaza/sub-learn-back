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
    private Long collectionId;
    private String collectionName;
    private Integer percent;
    private LocalDateTime created;


    public static TrialCondensedDto from(TrialEntity entity) {
        return new TrialCondensedDto(
                entity.getId(),
                entity.getEpisodeEntity().getId(),
                "ss",
                entity.getPercent(),
                entity.getCreated()
        );
    }

    public static TrialCondensedDto from(TrialDto dto) {
        return new TrialCondensedDto(
                dto.getId(),
                dto.getEpisodeDto().getId(),
                "dd",
                dto.getPercent(),
                dto.getCreated()
        );
    }

    public static TrialCondensedDto from(TrialWordDto trialWordDto) {
        return new TrialCondensedDto(
                trialWordDto.getId(),
                trialWordDto.getTrialDto().getEpisodeDto().getId(),
                trialWordDto.getTrialDto().getEpisodeDto().getCollectionDto().getName(),
                trialWordDto.getTrialDto().getPercent(),
                trialWordDto.getTrialDto().getCreated()
        );
    }

}
