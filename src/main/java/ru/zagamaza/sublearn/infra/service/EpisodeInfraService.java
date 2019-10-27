package ru.zagamaza.sublearn.infra.service;


import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.zagamaza.sublearn.dto.EpisodeDto;

import java.util.List;

public interface EpisodeInfraService {

    EpisodeDto get(Long id);

    List<EpisodeDto> getAll(Pageable pageable);

    @Transactional
    EpisodeDto update(EpisodeDto dto);

    @Transactional
    EpisodeDto saveAfterTranslator(EpisodeDto updateDto);

    EpisodeDto save(EpisodeDto dto);

    void removeById(long id);

    EpisodeDto parseAndSave(Long id, MultipartFile file);

    List<EpisodeDto> getAllByCollectionId(Long collectionId, Pageable pageable);

    Integer getCountByCollectionId(Long collectionId);

    Integer getStatistic(Long id, Long userId);

    List<Integer> getSeasonsByCollectionId(Long collectionId);

    List<EpisodeDto> getAllByCollectionIdAndSeason(Long collectionId, Integer season, Pageable pageable);

    Integer getCountByCollectionIdAndSeason(Long collectionId, Integer season);

}
