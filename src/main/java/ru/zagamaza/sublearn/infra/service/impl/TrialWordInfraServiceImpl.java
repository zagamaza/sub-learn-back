package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.domain.exception.InvalidRequestException;
import ru.zagamaza.sublearn.domain.exception.NotFoundException;
import ru.zagamaza.sublearn.dto.TrialDto;
import ru.zagamaza.sublearn.dto.TrialWordDto;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.infra.dao.entity.TrialWordEntity;
import ru.zagamaza.sublearn.infra.dao.repository.TrialWordRepository;
import ru.zagamaza.sublearn.infra.service.api.TrialWordInfraService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class TrialWordInfraServiceImpl implements TrialWordInfraService {

    private final TrialWordRepository repository;
    private final MessageSource messageSource;

    @Override
    public TrialWordDto get(Long id) {
        TrialWordEntity entity = repository.findById(id)
                                           .orElseThrow(() -> new NotFoundException(getMessage(
                                                   "trialWord.not.found.exception", id
                                           )));
        return TrialWordDto.from(entity);
    }

    @Override
    public List<TrialWordDto> getAll(Pageable pageable) {
        Page<TrialWordEntity> entities = repository.findAll(pageable);
        return entities
                .stream()
                .filter(Objects::nonNull)
                .map(TrialWordDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public TrialWordDto save(TrialWordDto dto) {
        TrialWordEntity entity = repository.save(TrialWordEntity.from(dto));
        return TrialWordDto.from(entity);
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<TrialWordDto> save20WordTrialForTrial(TrialDto trialDto) {
        List<Long> wordIds = repository.getWordIdsForTrial(trialDto.getId());
        if (isEmpty(wordIds)) {
            throw new InvalidRequestException(getMessage("trialWord.not.free.word.exception", trialDto.getId()));
        }
        return wordIds.stream()
                      .map(wordId -> TrialWordDto.builder()
                                                 .wordDto(WordDto.builder().id(wordId).build())
                                                 .trialDto(trialDto).created(LocalDateTime.now())
                                                 .build())
                      .map(this::save)
                      .collect(Collectors.toList());
    }


    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
