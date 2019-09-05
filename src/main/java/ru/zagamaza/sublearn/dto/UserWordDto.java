package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zagamaza.sublearn.infra.dao.entity.UserWordEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWordDto {

    private Long id;

    private UserDto userDto;

    private WordDto wordDto;

    private Integer rate;

    public UserWordDto(Long userId, Long wordId) {
        this.userDto = UserDto.builder().id(userId).build();
        this.wordDto = WordDto.builder().id(wordId).build();
    }

    public static UserWordDto from(UserWordEntity entity) {
        return new UserWordDto(
                entity.getId(),
                UserDto.from(entity.getUserEntity()),
                WordDto.from(entity.getWordEntity()),
                entity.getRate()
        );
    }

    public static UserWordDto from(UserWordRequest dto) {
        return new UserWordDto(
                dto.getId(),
                UserDto.builder().id(dto.getUserId()).build(),
                WordDto.builder().id(dto.getWordId()).build(),
                null
        );
    }

}
