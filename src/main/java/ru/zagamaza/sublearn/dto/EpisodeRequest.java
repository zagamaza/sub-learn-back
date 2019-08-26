package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeRequest {

    private Long id;

    @NotNull
    private List<WordDto> words;

    @NotNull
    private Long collectionId;

    @Min(value = 1L)
    @Max(value = 23L)
    private Integer season;

    @Min(value = 1L)
    @Max(value = 50L)
    private Integer episode;

}
