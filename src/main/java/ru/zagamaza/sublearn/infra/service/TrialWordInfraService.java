package ru.zagamaza.sublearn.infra.service;


import org.springframework.data.domain.Pageable;
import ru.zagamaza.sublearn.dto.TrialDto;
import ru.zagamaza.sublearn.dto.TrialWordDto;

import java.util.List;

public interface TrialWordInfraService {

    TrialWordDto get(Long id);

    List<TrialWordDto> getAll(Pageable pageable);

    TrialWordDto update(TrialWordDto dto);

    TrialWordDto updateTrialWordAndSaveUserWord(TrialWordDto dto);

    TrialWordDto save(TrialWordDto dto);

    void removeById(long id);

    List<TrialWordDto> saveWordTrialsForTrial(TrialDto trialDto);

    TrialWordDto updateTrialWordAndSaveLearnedUserWord(Long id);

}
