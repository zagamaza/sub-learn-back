package ru.zagamaza.sublearn.infra.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.zagamaza.sublearn.dto.TranslateOptionDto;
import ru.zagamaza.sublearn.dto.TrialCondensedDto;
import ru.zagamaza.sublearn.dto.TrialDto;

public interface TrialInfraService {

    TrialDto get(Long id);

    TrialDto getWithWord(Long id);

    Page<TrialDto> getAll(Pageable pageable);

    TrialDto saveTrialAndTrialWords(TrialDto dto);

    TrialDto save(TrialDto dto);

    void removeById(long id);

    TranslateOptionDto getNextWord(Long trialId);

    Page<TrialCondensedDto> getNotFinishConsedTrialByUserId(Long userId, Pageable pageable);

    void fillStatistic(TrialDto trialDto);

    TrialDto getLastNotFinishTrialByEpisodeIdAndUserId(Long userId, Long episodeId);

}
