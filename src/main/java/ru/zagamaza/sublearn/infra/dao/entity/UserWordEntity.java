package ru.zagamaza.sublearn.infra.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.dto.UserWordDto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_word", indexes = {
        @Index(name = "user_word_word_id_ix", columnList = "word_id"),
        @Index(name = "user_word_user_id_ix", columnList = "user_id")
})
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
