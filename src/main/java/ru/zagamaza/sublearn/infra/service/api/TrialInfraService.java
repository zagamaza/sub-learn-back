package ru.zagamaza.sublearn.infra.service.api;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.dto.TranslateOptionDto;
import ru.zagamaza.sublearn.dto.TrialCondensedDto;
import ru.zagamaza.sublearn.dto.TrialDto;

import java.util.List;

public interface TrialInfraService {

    TrialDto get(Long id);

    List<TrialDto> getAll(Pageable pageable);

    TrialDto saveTrialAnd20TrialWord(TrialDto dto);

    @Transactional
    TrialDto save(TrialDto dto);

    void removeById(long id);

    TranslateOptionDto getNextWord(Long trialId);

    List<TrialCondensedDto> getLastConsedTrialByUserId(Long userId, Pageable pageable);
}
