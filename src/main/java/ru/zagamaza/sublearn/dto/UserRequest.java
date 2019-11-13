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
public class UserRequest {

    private Long id;

    private Long telegramId;

    @NotNull
    private String userName;

    @Email
    private String email;

    private LocalDateTime created;


}
