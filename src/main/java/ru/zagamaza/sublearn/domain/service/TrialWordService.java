package ru.zagamaza.sublearn.domain.service;

import ru.zagamaza.sublearn.dto.TrialDto;
import ru.zagamaza.sublearn.dto.TrialWordDto;

import java.util.List;

public interface TrialWordService {

    List<TrialWordDto> fillTrialWordDto(List<Long> wordIds, TrialDto trialDto);

    TrialWordDto updateTrialWord(TrialWordDto saveDto, TrialWordDto updateDto);

}
