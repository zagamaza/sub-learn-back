package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import java.math.BigInteger;
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
    public Page<TrialDto> getAll(Pageable pageable) {
        Page<TrialEntity> entities = repository.findAll(pageable);
        return new PageImpl<>(entities
                                      .stream()
                                      .map(TrialDto::compressedFrom)
                                      .collect(Collectors.toList()), pageable, entities.getTotalElements());
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
    public Page<TrialCondensedDto> getNotFinishConsedTrialByUserId(Long userId, Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("episode_id")
        );
        Page<BigInteger> trialIds = repository.findNotFinishTrialIdsByUserId(userId, pageRequest);
        return new PageImpl<>(trialIds.stream()
                                      .map(this::getTrialCondensedDto)
                                      .peek(t -> t.setCollectionName(repository.getTrialName(t.getId())))
                                      .collect(Collectors.toList()), pageable, trialIds.getTotalElements());
    }

    @Override
    public List<Boolean> getTrialWordStatusByTrialId(Long trialId) {
        return repository.getTrialWordStatus(trialId);
    }

    private TrialCondensedDto getTrialCondensedDto(BigInteger id) {
        TrialEntity entity = repository.findById(id.longValue())
                                       .orElseThrow(() -> new NotFoundException(getMessage(
                                               "trial.not.found.exception", id
                                       )));
        return TrialCondensedDto.from(entity);

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
