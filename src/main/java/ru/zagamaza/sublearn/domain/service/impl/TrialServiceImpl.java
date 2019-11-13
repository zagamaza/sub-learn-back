package ru.zagamaza.sublearn.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.domain.service.TrialService;
import ru.zagamaza.sublearn.dto.TranslateOptionDto;
import ru.zagamaza.sublearn.dto.TrialCondensedDto;
import ru.zagamaza.sublearn.dto.TrialDto;
import ru.zagamaza.sublearn.dto.TrialWordDto;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.exception.domain.InvalidRequestException;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TrialServiceImpl implements TrialService {

    private final MessageSource messageSource;

    @Override
    public TranslateOptionDto fillTranslateOption(TrialWordDto trialWordDto, List<WordDto> randomWords) {

        TranslateOptionDto translateOptionDto = new TranslateOptionDto();
        translateOptionDto.setTrialCondensedDto(TrialCondensedDto.from(trialWordDto));
        translateOptionDto.setTranslatable(trialWordDto.getWordDto());
        translateOptionDto.setTrialWordId(trialWordDto.getId());
        translateOptionDto.setPresent(trialWordDto.getTrialDto().getPercent());
        translateOptionDto.setCorrectPercent(trialWordDto.getTrialDto().getCorrectPercent());
        translateOptionDto.setTranslations(randomWords);
        translateOptionDto.setLastWord(trialWordDto.isLastWord());
        return translateOptionDto;
    }

    @Override
    public TrialWordDto getTrialWordNotIsPassed(TrialDto trialDto) {
        return trialDto.getTrialWords()
                       .stream()
                       .filter(trialWord -> !trialWord.isPassed())
                       .findFirst()
                       .orElseThrow(() -> new InvalidRequestException(getMessage(
                               "trial.finished.exception", trialDto.getId()
                       )));

    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
