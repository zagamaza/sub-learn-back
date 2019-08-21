package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrialRequestDto {

    private Long id;

    private String name;

    private Long userId;

    private Long collectionId;

    private LocalDateTime created;

}
