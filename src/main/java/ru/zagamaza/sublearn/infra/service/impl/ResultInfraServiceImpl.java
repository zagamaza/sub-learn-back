package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.domain.exception.NotFoundException;
import ru.zagamaza.sublearn.dto.ResultDto;
import ru.zagamaza.sublearn.infra.dao.entity.ResultEntity;
import ru.zagamaza.sublearn.infra.dao.repository.ResultRepository;
import ru.zagamaza.sublearn.infra.service.api.ResultInfraService;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultInfraServiceImpl implements ResultInfraService {

    private final ResultRepository repository;
    private final MessageSource messageSource;

    @Override
    public ResultDto get(Long id) {
        ResultEntity entity = repository.findById(id)
                                        .orElseThrow(() -> new NotFoundException(getMessage(
                                                "result.not.found.exception",
                                                id
                                        )));
        return ResultDto.from(entity);
    }

    @Override
    public List<ResultDto> getAll(Pageable pageable) {
        Page<ResultEntity> entities = repository.findAll(pageable);
        return entities
                .stream()
                .filter(Objects::nonNull)
                .map(ResultDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public ResultDto save(ResultDto dto) {
        ResultEntity entity = repository.save(ResultEntity.from(dto));
        return ResultDto.from(entity);
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }


    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
