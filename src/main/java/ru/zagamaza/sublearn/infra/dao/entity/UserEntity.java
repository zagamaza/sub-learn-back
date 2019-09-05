package ru.zagamaza.sublearn.infra.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.UserDto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long telegramId;

    private String userName;

    private String email;

    private LocalDateTime created;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE}, mappedBy = "userEntity")
    private UserSettingEntity userSettingEntity;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userEntity")
    private List<UserWordEntity> userWordEntities;

    public static UserEntity from(UserDto dto) {
        return UserEntity.builder()
                         .id(dto.getId())
                         .created(dto.getCreated())
                         .telegramId(dto.getTelegramId())
                         .userName(dto.getUserName())
                         .email(dto.getEmail())
                         .userSettingEntity(dto.getUserSettingDto() == null
                                                    ? null
                                                    : UserSettingEntity.from(dto.getUserSettingDto()))
                         .build();
    }

}
