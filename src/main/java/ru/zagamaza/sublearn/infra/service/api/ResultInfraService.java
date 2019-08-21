package ru.zagamaza.sublearn.infra.service.api;


import org.springframework.data.domain.Pageable;
import ru.zagamaza.sublearn.dto.ResultDto;

import java.util.List;

public interface ResultInfraService {

    ResultDto get(Long id);

    List<ResultDto> getAll(Pageable pageable);

    ResultDto save(ResultDto dto);

    void removeById(long id);
}
