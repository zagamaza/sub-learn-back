package ru.zagamaza.sublearn.infra.service;

import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.infra.dao.entity.WordEntity;

import java.util.List;

public interface WordInfraService {

    WordDto get(Long id);

    WordDto save(WordDto dto);

    void removeById(long id);

    WordDto getByName(String name);

    List<WordDto> getRandomWordsByEpisodeId(Long episodeId, Long exclusionWordId, Integer countWord);

    List<String> removeWordsAlreadySave(List<String> words, List<WordDto> wordDtos);

    @Transactional
    List<WordEntity> fillMainTranslation(Integer seek);

}
