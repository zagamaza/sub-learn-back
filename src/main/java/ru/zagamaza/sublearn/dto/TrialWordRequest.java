package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrialWordRequest {

    private Long id;

    private Long wordId;

    private Long trialId;

    @NotNull
    boolean isRight;

    @NotNull
    boolean isPassed;

    private LocalDateTime created;

}
