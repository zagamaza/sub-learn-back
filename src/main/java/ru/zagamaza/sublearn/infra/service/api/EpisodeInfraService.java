package ru.zagamaza.sublearn.infra.service.api;


import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.dto.EpisodeDto;

import java.util.List;

public interface EpisodeInfraService {

    EpisodeDto get(Long id);

    List<EpisodeDto> getAll(Pageable pageable);

    @Transactional
    EpisodeDto update(EpisodeDto dto);

    EpisodeDto save(EpisodeDto dto);

    void removeById(long id);

}
