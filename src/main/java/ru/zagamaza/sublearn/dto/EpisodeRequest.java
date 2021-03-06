package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeRequest {

    private Long id;

    private Set<WordDto> words;

    @NotNull
    private Long collectionId;

    @Min(value = 0L)
    @Max(value = 39L)
    private Integer season;

    @Min(value = 0L)
    @Max(value = 60L)
    private Integer episode;

}
