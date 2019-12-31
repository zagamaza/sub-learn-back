package ru.zagamaza.sublearn.infra.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.NotificationDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications", indexes = @Index(name = "notifications_user_id_ix", columnList = "user_id"))
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private LocalDateTime created;

    public static NotificationEntity from(NotificationDto dto) {
        return new NotificationEntity(
                dto.getId(),
                dto.getText(),
                UserEntity.builder()
                          .id(dto.getUserDto().getId())
                          .build(),
                dto.getNotificationType(),
                dto.getCreated()
        );
    }

}
