package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultRequestDto {

    private Long id;
    private Long wordId;
    private Long trialId;
    boolean isRight;
    private LocalDateTime created;

}
