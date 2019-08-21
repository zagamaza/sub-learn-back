package ru.zagamaza.sublearn.infra.service.api;

import org.springframework.data.domain.Pageable;
import ru.zagamaza.sublearn.dto.TranslateOptionDto;
import ru.zagamaza.sublearn.dto.TrialCondensedDto;
import ru.zagamaza.sublearn.dto.TrialDto;

import java.util.List;

public interface TrialInfraService {

    TranslateOptionDto continueTrial(TrialDto trialDto);

    TrialDto get(Long id);

    List<TrialDto> getAll(Pageable pageable);

    List<TrialDto> getAllByUserId(Long userId, Pageable pageable);

    TrialDto save(TrialDto dto);

    void removeById(long id);

    TranslateOptionDto getNextWord(Long trialId);

    List<TrialCondensedDto> getLastConsedTrialByUserId(Long userId, Pageable pageable);
}
