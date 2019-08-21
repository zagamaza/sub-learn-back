package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.domain.exception.NotFoundException;
import ru.zagamaza.sublearn.domain.service.TrialService;
import ru.zagamaza.sublearn.dto.TranslateOptionDto;
import ru.zagamaza.sublearn.dto.TrialCondensedDto;
import ru.zagamaza.sublearn.dto.TrialDto;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.infra.dao.entity.TrialEntity;
import ru.zagamaza.sublearn.infra.dao.repository.TrialRepository;
import ru.zagamaza.sublearn.infra.service.api.CollectionInfraService;
import ru.zagamaza.sublearn.infra.service.api.TrialInfraService;
import ru.zagamaza.sublearn.infra.service.api.WordInfraService;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrialInfraServiceImpl implements TrialInfraService {

    private final TrialRepository repository;
    private final TrialService trialService;
    private final WordInfraService wordInfraService;
    private final CollectionInfraService collectionInfraService;
    private final MessageSource messageSource;


    @Override
    public TranslateOptionDto continueTrial(TrialDto trialDto) {
        TrialDto dto = save(trialDto);
        return trialService.fillTranslateOption(new WordDto(), dto);
    }

    @Override
    public TrialDto get(Long id) {
        TrialEntity entity = repository.findById(id)
                                       .orElseThrow(() -> new NotFoundException(getMessage(
                                               "trial.not.found.exception",
                                               id
                                       )));

        return TrialDto.from(entity);
    }

    @Override
    public List<TrialDto> getAll(Pageable pageable) {
        Page<TrialEntity> entities = repository.findAll(pageable);
        return entities
                .stream()
                .map(TrialDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrialDto> getAllByUserId(Long userId, Pageable pageable) {
        List<TrialEntity> entities = repository.findAllByUserEntityIdOrderByCreatedDesc(userId, pageable);
        return entities
                .stream()
                .filter(Objects::nonNull)
                .map(TrialDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TrialDto save(TrialDto dto) {
        TrialEntity entity;
        entity = repository.save(TrialEntity.from(dto));
        TrialDto trialDto = TrialDto.from(entity);
        trialDto.setCollectionDto(collectionInfraService.get(trialDto.getCollectionDto().getId()));
        return trialDto;
    }

    @Override
    @Transactional
    public void removeById(long id) {
        repository.deleteById(id);
    }

    @Override
    public TranslateOptionDto getNextWord(Long trialId) {
        Long lastWordId = repository.getLastWordId(trialId);
        if (lastWordId == null) {
            TrialDto trialDto = get(trialId);
            return TranslateOptionDto.from(trialDto);
        }
        WordDto wordDto = wordInfraService.get(lastWordId);
        return trialService.fillTranslateOption(wordDto, get(trialId));
    }

    @Override
    public List<TrialCondensedDto> getLastConsedTrialByUserId(Long userId, Pageable pageable) {
        List<TrialEntity> entities = repository.findAllByUserEntityIdOrderByCreatedDesc(userId, pageable);
        return entities
                .stream()
                .filter(Objects::nonNull)
                .map(TrialCondensedDto::from)
                .collect(Collectors.toList());
    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
