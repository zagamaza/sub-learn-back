package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.UserWordEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWordRequest {

    private Long id;

    private Long userId;

    private Long wordId;
}
