package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.UserEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private Long telegramId;

    @NotNull
    private String userName;

    @Email
    private String email;

    private LocalDateTime created;

    private UserSettingDto userSettingDto;

    public static UserDto from(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserDto(
                entity.getId(),
                entity.getTelegramId(),
                entity.getUserName(),
                entity.getEmail(),
                entity.getCreated(),
                entity.getUserSettingEntity() == null
                        ? new UserSettingDto(null, entity.getId(), 20, 4, false, 75, false, 3)
                        : UserSettingDto.from(entity.getUserSettingEntity())
        );
    }

    public static UserDto from(UserRequest userRequest) {
        return new UserDto(
                userRequest.getId(),
                userRequest.getTelegramId(),
                userRequest.getUserName(),
                userRequest.getEmail(),
                userRequest.getCreated(),
                null
        );
    }

    public static UserDto compressedFrom(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserDto(
                entity.getId(),
                entity.getTelegramId(),
                entity.getUserName(),
                entity.getEmail(),
                entity.getCreated(),
                null
        );

    }

}
