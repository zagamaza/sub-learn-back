package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.TrialEntity;
import ru.zagamaza.sublearn.infra.dao.entity.UserEntity;

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

    private EpisodeDto episodeDto;

    private List<TrialWordDto> trialWords;

    private UserDto userDto;

    private Integer percent;

    private Integer correctPercent;

    private LocalDateTime created;

    public TrialDto(Long trialId) {
        this.id = trialId;
    }


    public static TrialDto from(TrialEntity entity) {
        if (entity == null){
            return null;
        }
        return new TrialDto(
                entity.getId(),
                entity.getName(),
                EpisodeDto.from(entity.getEpisodeEntity()),
                isEmpty(entity.getTrialWordEntity())
                        ? null
                        : entity.getTrialWordEntity().stream()
                                .map(TrialWordDto::from)
                                .collect(Collectors.toList()),
                UserDto.from(entity.getUserEntity()),
                entity.getPercent(),
                entity.getCorrectPercent(),
                entity.getCreated()
        );

    }

    public static TrialDto compressedFrom(TrialEntity entity) {
        return new TrialDto(
                entity.getId(),
                entity.getName(),
                EpisodeDto.compressedFrom(entity.getEpisodeEntity()),
                null,
                UserDto.from(entity.getUserEntity()),
                entity.getPercent(),
                entity.getCorrectPercent(),
                entity.getCreated()
        );
    }

    public static TrialDto from(TrialRequest trialRequest) {
        return new TrialDto(
                trialRequest.getId(),
                trialRequest.getName(),
                EpisodeDto.builder().id(trialRequest.getEpisodeId()).build(),
                null,
                UserDto.builder().id(trialRequest.getUserId()).build(),
                null,
                null,
                trialRequest.getCreated()
        );

    }

}
