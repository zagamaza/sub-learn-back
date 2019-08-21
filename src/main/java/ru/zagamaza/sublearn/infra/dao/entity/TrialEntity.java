package ru.zagamaza.sublearn.infra.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import ru.zagamaza.sublearn.dto.TrialDto;

import javax.persistence.*;
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

    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "collection_id", nullable = false)
    private CollectionEntity collectionEntity;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<ResultEntity> resultEntity;

    @Formula("(select count(1)+1\n" +
            "        from trials t\n" +
            "                 join results r on t.id = r.trial_id\n" +
            "                 join words w2 on r.word_id = w2.id\n" +
            "        where t.id = id) *100/ (select count(1)+1\n" +
            "                                  from trials t\n" +
            "                                           join collections c on t.collection_id = c.id\n" +
            "                                           join collections_world_entities cwe on c.id = cwe.collection_entity_id\n" +
            "                                           join words w on cwe.world_entities_id = w.id\n" +
            "                                  where t.id = id)")
    private Integer percent;

    @Formula("(select count(1) + 1 from results o where o.trial_id = id and o.is_right is true)*100/" +
            "(select count(1) + 1  from results o where o.trial_id = id)")
    private Integer correctPercent;

    private LocalDateTime created;


    public static TrialEntity from(TrialDto dto) {
        return new TrialEntity(
                dto.getId(),
                dto.getName(),
                UserEntity.from(dto.getUserDto()),
                CollectionEntity.compressedFrom(dto.getCollectionDto()),
                isEmpty(dto.getResults())
                        ? null
                        : dto.getResults().stream()
                        .map(ResultEntity::from)
                        .collect(Collectors.toList()),
                dto.getPercent(),
                dto.getPercent(),
                dto.getCreated()
        );

    }

}
