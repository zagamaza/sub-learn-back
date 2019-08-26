package ru.zagamaza.sublearn.infra.service.api;

import ru.zagamaza.sublearn.dto.WordDto;

import java.util.List;

public interface WordInfraService {

    WordDto get(Long id);

    WordDto save(WordDto dto);

    void removeById(long id);

    WordDto getByName(String name);

    List<WordDto> getRandomWordsByEpisodeId(Long episodeId, Integer countWord);

}
