package ru.zagamaza.sublearn.infra.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.ResultDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "results")
public class ResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", nullable = false)
    private WordEntity wordEntity;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "trial_id", nullable = false)
    private TrialEntity trialEntity;

    @Column(name = "is_right")
    boolean isRight;

    private LocalDateTime created;

    public static ResultEntity from(ResultDto dto) {
        return ResultEntity.builder()
                .created(LocalDateTime.now())
                .isRight(dto.isRight())
                .wordEntity(WordEntity.from(dto.getWordDto()))
                .trialEntity(TrialEntity.builder().id(dto.getTrialDto().getId()).build())
                .build();
    }


}
