package ru.zagamaza.sublearn.domain.service;

import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.dto.EpisodeDto;
import ru.zagamaza.sublearn.dto.TranslateOptionDto;
import ru.zagamaza.sublearn.dto.TrialCondensedDto;
import ru.zagamaza.sublearn.dto.TrialDto;
import ru.zagamaza.sublearn.dto.TrialWordDto;
import ru.zagamaza.sublearn.dto.WordDto;

import java.util.ArrayList;
import java.util.List;

public interface TrialService {

    TranslateOptionDto fillTranslateOption(TrialWordDto trialWordDto, List<WordDto> randomWords);

    TrialWordDto getTrialWordNotIsPassed(TrialDto trialDto);

}
