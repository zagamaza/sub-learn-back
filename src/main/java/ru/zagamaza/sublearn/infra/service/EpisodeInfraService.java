package ru.zagamaza.sublearn.infra.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.zagamaza.sublearn.dto.EpisodeDto;

import java.util.List;

public interface EpisodeInfraService {

    EpisodeDto get(Long id);

    EpisodeDto getWithWords(Long id);

    List<EpisodeDto> getAll(Pageable pageable);

    @Transactional
    EpisodeDto update(EpisodeDto dto);

    @Transactional
    EpisodeDto saveAfterTranslator(EpisodeDto updateDto);

    EpisodeDto save(EpisodeDto dto);

    void removeById(long id);

    EpisodeDto parseAndSave(Long id, MultipartFile file);

    Page<EpisodeDto> getAllByCollectionId(Long collectionId, Pageable pageable);

    Integer getStatistic(Long id, Long userId);

    Page<Integer> getSeasonsByCollectionId(Long collectionId, Pageable pageable);

    Page<EpisodeDto> getAllByCollectionIdAndSeason(Long collectionId, Integer season, Pageable pageable);

    EpisodeDto getByCollectionIdAndSeasonAndSeries(Long collectionId, Integer season, Integer series);

    Boolean isEmptyEpisode(Long id);

}
