package ru.zagamaza.sublearn.infra.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import ru.zagamaza.sublearn.dto.TrialDto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trials")
public class TrialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "episode_id", nullable = false)
    private EpisodeEntity episodeEntity;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy = "trialEntity")
    private List<TrialWordEntity> trialWordEntity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    private LocalDateTime created;

    public static TrialEntity from(TrialDto dto) {
        return new TrialEntity(
                dto.getId(),
                dto.getName(),
                EpisodeEntity.builder().id(dto.getEpisodeDto().getId()).build(),
                isEmpty(dto.getTrialWords())
                        ? null
                        : dto.getTrialWords().stream()
                             .map(TrialWordEntity::from)
                             .collect(Collectors.toList()),
                UserEntity.builder().id(dto.getUserDto().getId()).build(),
                dto.getCreated()
        );

    }

}
