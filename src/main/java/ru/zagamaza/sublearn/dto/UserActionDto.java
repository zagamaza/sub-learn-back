package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.ActionType;
import ru.zagamaza.sublearn.infra.dao.entity.UserActionEntity;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserActionDto {

    private Long id;

    @NotNull
    private String data;

    @NotNull
    private Long userId;

    @NotNull
    private ActionType actionType;

    private LocalDateTime created;

    public static UserActionDto from(UserActionEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserActionDto(
                entity.getId(),
                entity.getData(),
                entity.getUserEntity().getId(),
                entity.getActionType(),
                entity.getCreated()
        );

    }

}
