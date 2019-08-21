package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String email;

    private LocalDateTime created;

    private List<TrialDto> passages;

    public static UserDto from(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getEmail(),
                entity.getCreated(),
                isEmpty(entity.getTrialEntities())
                        ? null
                        : entity.getTrialEntities().stream()
                        .map(TrialDto::from)
                        .collect(Collectors.toList())
        );
    }

    public static UserDto compressedFrom(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getEmail(),
                entity.getCreated(),
                null
        );
    }

}
