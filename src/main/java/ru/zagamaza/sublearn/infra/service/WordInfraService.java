package ru.zagamaza.sublearn.infra.service;

import ru.zagamaza.sublearn.dto.WordDto;

import java.util.List;

public interface WordInfraService {

    WordDto get(Long id);

    WordDto save(WordDto dto);

    void removeById(long id);

    WordDto getByName(String name);

    List<WordDto> getRandomWordsByEpisodeId(Long episodeId, Long exclusionWordId, Integer countWord);

    List<String> removeWordsAlreadySave(List<String> words, List<WordDto> wordDtos);

}
