package ru.zagamaza.sublearn.domain.service;

import ru.zagamaza.sublearn.dto.UserWordDto;

public interface UserWordService {

    UserWordDto calculateRate(UserWordDto dto, boolean isRight);


}
