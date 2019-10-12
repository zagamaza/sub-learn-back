package ru.zagamaza.sublearn.infra.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.domain.service.TrialWordService;
import ru.zagamaza.sublearn.dto.TrialDto;
import ru.zagamaza.sublearn.dto.TrialWordDto;
import ru.zagamaza.sublearn.dto.UserSettingDto;
import ru.zagamaza.sublearn.exception.domain.NotFoundException;
import ru.zagamaza.sublearn.infra.dao.entity.TrialWordEntity;
import ru.zagamaza.sublearn.infra.dao.repository.TrialWordRepository;
import ru.zagamaza.sublearn.infra.service.TrialInfraService;
import ru.zagamaza.sublearn.infra.service.TrialWordInfraService;
import ru.zagamaza.sublearn.infra.service.UserSettingInfraService;
import ru.zagamaza.sublearn.infra.service.UserWordInfraService;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TrialWordInfraServiceImpl implements TrialWordInfraService {

    private UserWordInfraService userWordInfraService;
    private UserSettingInfraService userSettingInfraService;
    private TrialInfraService trialInfraService;
    private TrialWordService trialWordService;
    private TrialWordRepository repository;
    private MessageSource messageSource;

    public TrialWordInfraServiceImpl(
            UserWordInfraService userWordInfraService,
            UserSettingInfraService userSettingInfraService,
            @Lazy TrialInfraService trialInfraService,
            TrialWordService trialWordService,
            TrialWordRepository repository,
            MessageSource messageSource
    ) {
        this.userWordInfraService = userWordInfraService;
        this.userSettingInfraService = userSettingInfraService;
        this.trialInfraService = trialInfraService;
        this.trialWordService = trialWordService;
        this.repository = repository;
        this.messageSource = messageSource;
    }

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
        return entities.stream()
                       .filter(Objects::nonNull)
                       .map(TrialWordDto::from)
                       .collect(Collectors.toList());
    }

    @Override
    public TrialWordDto update(TrialWordDto updateDto) {
        TrialWordDto saveDto = get(updateDto.getId());
        trialWordService.updateTrialWord(saveDto, updateDto);
        return save(saveDto);
    }

    @Override
    public TrialWordDto updateTrialWordAndSaveUserWord(TrialWordDto dto) {
        TrialWordDto trialWordDto = update(dto);
        updateUserWord(trialWordDto);
        trialInfraService.fillStatistic(trialWordDto.getTrialDto());
        return trialWordDto;
    }

    private void updateUserWord(TrialWordDto trialWordDto) {
        Long userId = repository.getUserIdById(trialWordDto.getId());
        userWordInfraService.save(trialWordDto.getWordDto().getId(), userId, trialWordDto.isRight());
    }

    @Override
    @Transactional
    public TrialWordDto save(TrialWordDto dto) {
        TrialWordEntity entity = repository.save(TrialWordEntity.from(dto));
        return TrialWordDto.from(entity);
    }

    @Override
    public void removeById(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<TrialWordDto> saveWordTrialsForTrial(TrialDto trialDto) {
        UserSettingDto userSettingDto = userSettingInfraService.getByTrialId(trialDto.getId());
        List<Long> wordIds = repository.getWordIdsForTrial(
                trialDto.getId(),
                PageRequest.of(0, userSettingDto.getWordCountInTrial())
        );
        List<TrialWordDto> trialWords = trialWordService.fillTrialWordDto(wordIds, trialDto);
        return trialWords.stream().map(this::save).collect(Collectors.toList());
    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
