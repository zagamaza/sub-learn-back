package ru.zagamaza.sublearn.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.domain.service.TrialWordService;
import ru.zagamaza.sublearn.dto.TrialDto;
import ru.zagamaza.sublearn.dto.TrialWordDto;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.exception.domain.InvalidRequestException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class TrialWordServiceImpl implements TrialWordService {

    private final MessageSource messageSource;

    @Override
    public List<TrialWordDto> fillTrialWordDto(List<Long> wordIds, TrialDto trialDto) {
        if (isEmpty(wordIds)) {
            throw new InvalidRequestException(getMessage("trialWord.not.free.word.exception", trialDto.getId()));
        }
        return wordIds.stream()
                      .map(wordId -> TrialWordDto.builder()
                                                 .wordDto(WordDto.builder().id(wordId).build())
                                                 .trialDto(trialDto)
                                                 .created(LocalDateTime.now())
                                                 .build())
                      .collect(Collectors.toList());
    }

    @Override
    public TrialWordDto updateTrialWord(TrialWordDto saveDto, TrialWordDto updateDto) {
        saveDto.setPassed(updateDto.isPassed());
        saveDto.setRight(updateDto.isRight());
        return saveDto;

    }


    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
