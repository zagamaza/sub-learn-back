package ru.zagamaza.sublearn.domain.service;

import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.dto.TranslateOptionDto;
import ru.zagamaza.sublearn.dto.TrialCondensedDto;
import ru.zagamaza.sublearn.dto.TrialWordDto;
import ru.zagamaza.sublearn.dto.WordDto;

import java.util.List;

@Service
public class TrialServiceImpl implements TrialService {

    @Override
    public TranslateOptionDto fillTranslateOption(TrialWordDto trialWordDto, List<WordDto> randomWords) {

        TranslateOptionDto translateOptionDto = new TranslateOptionDto();
        translateOptionDto.setTrialCondensedDto(TrialCondensedDto.from(trialWordDto));
        translateOptionDto.setTranslatable(trialWordDto.getWordDto());
        translateOptionDto.setPresent(trialWordDto.getTrialDto().getPercent());
        translateOptionDto.setCorrectPercent(trialWordDto.getTrialDto().getCorrectPercent());
        translateOptionDto.setTranslations(randomWords);
        return translateOptionDto;
    }

}
