package ru.zagamaza.sublearn.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.domain.service.UserWordService;
import ru.zagamaza.sublearn.dto.UserWordDto;

@Service
@RequiredArgsConstructor
public class UserWordServiceImpl implements UserWordService {

    private static final int RATE_FOR_CORRECT_ANSWER = 1;
    private static final int NEW_RATE = 0;
    private static final int RATE_FOR_INCORRECT_ANSWER = -2;
    private static final int FINAL_RATE = 10;

    @Override
    public UserWordDto calculateRate(UserWordDto dto, boolean isRight) {
        Integer rate = dto.getRate();
        if (rate != null) {
            if (rate == FINAL_RATE) {
                return dto;
            }
            rate += getRateByIsRight(isRight, false);
            dto.setRate(rate < 0 ? 0 : rate);
        } else {
            dto.setRate(getRateByIsRight(isRight, true));
        }
        return dto;
    }

    private int getRateByIsRight(boolean isRight, boolean isNew) {
        return isRight
                ? RATE_FOR_CORRECT_ANSWER
                : isNew ? NEW_RATE
                        : RATE_FOR_INCORRECT_ANSWER;
    }

}
