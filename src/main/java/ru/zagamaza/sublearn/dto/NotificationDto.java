package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.NotificationEntity;
import ru.zagamaza.sublearn.infra.dao.entity.NotificationType;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    private Long id;

    @NotNull
    private String text;

    private UserDto userDto;

    @NotNull
    private NotificationType notificationType;

    private LocalDateTime created;

    public static NotificationDto from(NotificationEntity entity) {
        if (entity == null) {
            return null;
        }
        return new NotificationDto(
                entity.getId(),
                entity.getText(),
                UserDto.compressedFrom(entity.getUserEntity()),
                entity.getNotificationType(),
                entity.getCreated()
        );

    }

}
