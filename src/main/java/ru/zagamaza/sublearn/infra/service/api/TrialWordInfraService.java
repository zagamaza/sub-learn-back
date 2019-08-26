package ru.zagamaza.sublearn.infra.service.api;


import org.springframework.data.domain.Pageable;
import ru.zagamaza.sublearn.dto.TrialDto;
import ru.zagamaza.sublearn.dto.TrialWordDto;

import java.util.List;

public interface TrialWordInfraService {

    TrialWordDto get(Long id);

    List<TrialWordDto> getAll(Pageable pageable);

    TrialWordDto save(TrialWordDto dto);

    void removeById(long id);

    List<TrialWordDto> save20WordTrialForTrial(TrialDto trialDto);

}
