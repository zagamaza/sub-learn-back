package ru.zagamaza.sublearn.infra.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.TrialWordDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trial_word")
public class TrialWordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "word_id", nullable = false)
    private WordEntity wordEntity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "trial_id", nullable = false)
    private TrialEntity trialEntity;

    @Column(name = "is_right")
    boolean isRight;

    @Column(name = "is_passed")
    boolean isPassed;

    private LocalDateTime created;

    public static TrialWordEntity from(TrialWordDto dto) {
        return TrialWordEntity.builder()
                              .id(dto.getId())
                              .created(LocalDateTime.now())
                              .isRight(dto.isRight())
                              .isPassed(dto.isPassed())
                              .wordEntity(WordEntity.from(dto.getWordDto()))
                              .trialEntity(TrialEntity.builder().id(dto.getTrialDto().getId()).build())
                              .build();
    }


}
