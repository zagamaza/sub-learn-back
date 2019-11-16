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
    private LocalDateTime created;


    public static TrialCondensedDto from(TrialEntity entity) {
        return new TrialCondensedDto(
                entity.getId(),
                entity.getEpisodeEntity().getId(),
                entity.getName(),
                entity.getCreated()
        );
    }

    public static TrialCondensedDto from(TrialDto dto) {
        return new TrialCondensedDto(
                dto.getId(),
                dto.getEpisodeDto().getId(),
                dto.getName(),
                dto.getCreated()
        );
    }

    public static TrialCondensedDto from(TrialWordDto trialWordDto) {
        TrialDto trialDto = trialWordDto.getTrialDto();
        return new TrialCondensedDto(
                trialDto.getId(),
                trialDto.getEpisodeDto().getId(),
                trialDto.getEpisodeDto().getCollectionDto().getName(),
                trialDto.getCreated()
        );
    }

}
