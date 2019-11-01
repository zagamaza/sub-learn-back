package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.domain.service.TrialService;
import ru.zagamaza.sublearn.dto.TranslateOptionDto;
import ru.zagamaza.sublearn.dto.TrialCondensedDto;
import ru.zagamaza.sublearn.dto.TrialDto;
import ru.zagamaza.sublearn.dto.TrialWordDto;
import ru.zagamaza.sublearn.dto.UserSettingDto;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.exception.domain.NotFoundException;
import ru.zagamaza.sublearn.infra.dao.entity.TrialEntity;
import ru.zagamaza.sublearn.infra.dao.repository.TrialRepository;
import ru.zagamaza.sublearn.infra.service.TrialInfraService;
import ru.zagamaza.sublearn.infra.service.TrialWordInfraService;
import ru.zagamaza.sublearn.infra.service.UserSettingInfraService;
import ru.zagamaza.sublearn.infra.service.WordInfraService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrialInfraServiceImpl implements TrialInfraService {

    private final TrialRepository repository;
    private final TrialService trialService;
    private final TrialWordInfraService trialWordInfraService;
    private final UserSettingInfraService userSettingInfraService;
    private final WordInfraService wordInfraService;
    private final MessageSource messageSource;

    @Override
    public TrialDto get(Long id) {
        TrialEntity entity = repository.findById(id)
                                       .orElseThrow(() -> new NotFoundException(getMessage(
                                               "trial.not.found.exception", id
                                       )));
        return TrialDto.compressedFrom(entity);
    }

    @Override
    public TrialDto getWithWord(Long id) {
        TrialEntity entity = repository.findById(id)
                                       .orElseThrow(() -> new NotFoundException(getMessage(
                                               "trial.not.found.exception", id
                                       )));
        return TrialDto.from(entity);
    }

    @Override
    public List<TrialDto> getAll(Pageable pageable) {
        Page<TrialEntity> entities = repository.findAll(pageable);
        return entities
                .stream()
                .map(TrialDto::compressedFrom)
                .collect(Collectors.toList());
    }

    @Override
    public TrialDto saveTrialAndTrialWords(TrialDto dto) {
        TrialDto trialDto = TrialDto.from(repository.save(TrialEntity.from(dto)));
        trialDto.setTrialWords(trialWordInfraService.saveWordTrialsForTrial(trialDto));
        return trialDto;
    }

    @Override
    @Transactional
    public TrialDto save(TrialDto dto) {
        TrialEntity entity;
        entity = repository.save(TrialEntity.from(dto));
        return TrialDto.compressedFrom(entity);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        repository.deleteById(id);
    }

    @Override
    public TranslateOptionDto getNextWord(Long trialId) {
        TrialDto trialDto = getWithWord(trialId);
        UserSettingDto userSettingDto = userSettingInfraService.getByTrialId(trialId);
        TrialWordDto trialWordDto = trialService.getTrialWordNotIsPassed(trialDto);
        trialWordDto.setTrialDto(trialDto);
        List<WordDto> randomWords = wordInfraService.getRandomWordsByEpisodeId(
                trialDto.getEpisodeDto().getId(),
                trialWordDto.getWordDto().getId(),
                userSettingDto.getAnswerOptionsCount() - 1
        );
        return trialService.fillTranslateOption(trialWordDto, randomWords);

    }

    @Override
    public List<TrialCondensedDto> getNotFinishConsedTrialByUserId(Long userId, Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("episode_id")
        );
        List<TrialDto> entities = repository.findNotFinishTrialIdsByUserId(userId, pageRequest)
                                            .stream()
                                            .map(this::get)
                                            .collect(Collectors.toList());
        List<TrialCondensedDto> list = new ArrayList<>();
        entities.stream()
                .map(TrialCondensedDto::from)
                .forEach(trialCondensedDto -> {
                    trialCondensedDto.setCollectionName(repository.getTrialName(trialCondensedDto.getId()));
                    list.add(trialCondensedDto);
                });
        return list;
    }

    // TODO: 08.09.2019 Костыль, который не знаю пока как решить/
    @Override
    public void fillStatistic(TrialDto trialDto) {
        trialDto.setCorrectPercent(repository.getCorrectPercent(trialDto.getId()));
        trialDto.setPercent(repository.getPercent(trialDto.getId()));
    }

    @Override
    public Integer getCountTrialByUserId(Long userId) {
        return repository.countNotFinishTrialByUserId(userId);
    }

    @Override
    public TrialDto getLastNotFinishTrialByEpisodeIdAndUserId(Long userId, Long episodeId) {
        Long trialId = repository.findFirstByUserEntityIdAndEpisodeEntityId(
                userId,
                episodeId
        );
        if (trialId == null) {
            return null;
        }
        return get(trialId);
    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
