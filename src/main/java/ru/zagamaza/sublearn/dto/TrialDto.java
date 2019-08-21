package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.TrialEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrialDto {

    private Long id;

    private String name;

    private UserDto userDto;

    private CollectionDto collectionDto;

    private List<ResultDto> results;

    private Integer percent;

    private Integer correctPercent;

    private LocalDateTime created;

    public TrialDto(Long trialId) {
        this.id = trialId;
    }


    public static TrialDto from(TrialEntity entity) {
        return new TrialDto(
                entity.getId(),
                entity.getName(),
                UserDto.compressedFrom(entity.getUserEntity()),
                CollectionDto.from(entity.getCollectionEntity()),
                isEmpty(entity.getResultEntity())
                        ? null
                        : entity.getResultEntity().stream()
                        .map(ResultDto::from)
                        .collect(Collectors.toList()),
                entity.getPercent(),
                entity.getCorrectPercent(),
                entity.getCreated()
        );

    }

    public static TrialDto compressedFrom(TrialEntity entity) {
        return new TrialDto(
                entity.getId(),
                entity.getName(),
                null,
                null,
                null,
                entity.getPercent(),
                entity.getCorrectPercent(),
                entity.getCreated()
        );
    }

    public static TrialDto from(TrialRequestDto trialRequestDto) {
        return new TrialDto(
                trialRequestDto.getId(),
                trialRequestDto.getName(),
                UserDto.builder().id(trialRequestDto.getUserId()).build(),
                CollectionDto.builder().id(trialRequestDto.getCollectionId()).build(),
                null,
                null,
                null,
                trialRequestDto.getCreated()
        );

    }
}
