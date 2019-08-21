package ru.zagamaza.sublearn.infra.service.api;

import ru.zagamaza.sublearn.dto.WordDto;

public interface WordInfraService {

    WordDto get(Long id);

    WordDto save(WordDto dto);

    void removeById(long id);

    WordDto getByName(String name);

}
