package ru.zagamaza.sublearn.infra.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.UserWordDto;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_word")
public class UserWordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "word_id", nullable = false)
    private WordEntity wordEntity;

    private Integer rate;

    public static UserWordEntity from(UserWordDto dto) {
        return new UserWordEntity(
                dto.getId(),
                UserEntity.builder().id(dto.getUserDto().getId()).build(),
                WordEntity.builder().id(dto.getWordDto().getId()).build(),
                dto.getRate()
        );
    }
}
