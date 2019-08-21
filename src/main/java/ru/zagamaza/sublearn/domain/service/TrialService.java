package ru.zagamaza.sublearn.domain.service;

import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.dto.CollectionDto;
import ru.zagamaza.sublearn.dto.TranslateOptionDto;
import ru.zagamaza.sublearn.dto.TrialCondensedDto;
import ru.zagamaza.sublearn.dto.TrialDto;
import ru.zagamaza.sublearn.dto.WordDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrialService {

    public TranslateOptionDto fillTranslateOption(WordDto wordDto, TrialDto trialDto) {
        CollectionDto collectionDto = trialDto.getCollectionDto();
        List<WordDto> words = collectionDto.getWords();
        TranslateOptionDto translateOptionDto = new TranslateOptionDto();
        translateOptionDto.setTrialCondensedDto(TrialCondensedDto.from(trialDto));
        translateOptionDto.setTranslatable(wordDto);
        translateOptionDto.setPresent(trialDto.getPercent());
        translateOptionDto.setCorrectPercent(trialDto.getCorrectPercent());
        List<WordDto> translationOptions = new ArrayList<>();
        translationOptions.add(wordDto);
        for (int i = 0; i < 3; i++) {
            translationOptions.add(words.get((int)(Math.random() * words.size())));
        }
        translateOptionDto.setTranslations(translationOptions);
        return translateOptionDto;
    }

}
