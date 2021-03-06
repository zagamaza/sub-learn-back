package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.zagamaza.sublearn.client.TranslatorClient;
import ru.zagamaza.sublearn.controller.EpisodeController;
import ru.zagamaza.sublearn.domain.service.EpisodeService;
import ru.zagamaza.sublearn.dto.EpisodeDto;
import ru.zagamaza.sublearn.dto.UserSettingDto;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.exception.domain.NotFoundException;
import ru.zagamaza.sublearn.infra.dao.entity.EpisodeEntity;
import ru.zagamaza.sublearn.infra.dao.repository.EpisodeRepository;
import ru.zagamaza.sublearn.infra.service.EpisodeInfraService;
import ru.zagamaza.sublearn.infra.service.UserSettingInfraService;
import ru.zagamaza.sublearn.infra.service.WordInfraService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EpisodeInfraServiceImpl implements EpisodeInfraService {

    private final EpisodeRepository repository;
    private final EpisodeService episodeService;
    private final MessageSource messageSource;
    private final WordInfraService wordInfraService;
    private final TranslatorClient translatorClient;
    private final UserSettingInfraService userSettingInfraService;

    @Override
    public EpisodeDto get(Long id) {
        EpisodeEntity entity = repository.findById(id)
                                         .orElseThrow(() -> new NotFoundException(
                                                 getMessage("episode.not.found.exception", id)
                                         ));
        return EpisodeDto.compressedFrom(entity);
    }

    @Override
    public EpisodeDto getWithWords(Long id) {
        EpisodeEntity entity = repository.findByIdWithWords(id)
                                         .orElseThrow(() -> new NotFoundException(
                                                 getMessage("episode.not.found.exception", id)
                                         ));
        return EpisodeDto.from(entity);
    }

    @Override
    public List<EpisodeDto> getAll(Pageable pageable) {
        Page<EpisodeEntity> entities = repository.findAll(pageable);
        return entities
                .stream()
                .map(EpisodeDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EpisodeDto update(EpisodeDto updateDto) {
        EpisodeDto saveDto = get(updateDto.getId());
        episodeService.updateEpisode(saveDto, updateDto);
        return save(saveDto);
    }

    @Override
    @Transactional
    public EpisodeDto saveAfterTranslator(EpisodeDto dto) {
        EpisodeEntity entity = EpisodeEntity.from(dto);
        entity = repository.save(entity);
        return EpisodeDto.compressedFrom(entity);
    }

    @Override
    @Transactional
    public EpisodeDto save(EpisodeDto dto) {
        EpisodeEntity entity = EpisodeEntity.from(dto);
        entity.setId(repository.save(new EpisodeEntity()).getId());
        dto.setId(entity.getId());
        if (!CollectionUtils.isEmpty(dto.getWords())) {
            dto.getWords()
               .forEach(word -> {
                   try {
                       WordDto wordDto = wordInfraService.getByName(word.getWord());
                       word.setWord(wordDto.getWord());
                       word.setId(wordDto.getId());
                   } catch (NotFoundException ignored) {}
               });
        }
        entity = repository.save(EpisodeEntity.from(dto));
        return EpisodeDto.from(entity);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public EpisodeDto parseAndSave(Long id, MultipartFile file) {
        EpisodeDto episodeDto = get(id);
        List<WordDto> wordDtos = new ArrayList<>();
        List<String> text = episodeService.parseWords(file);
        text = wordInfraService.removeWordsAlreadySave(text, wordDtos);
        List<WordDto> words = translatorClient
                .translateWithDictionaryAll(
                        text,
                        episodeDto.getCollectionDto().getLang()
                )
                .stream()
                .filter(wordDto -> wordDto.getWord() != null && wordDto.getTranslation() != null)
                .collect(Collectors.toList());
        wordDtos.addAll(words);
        episodeDto.setWords(new HashSet<>(wordDtos));
        return saveAfterTranslator(episodeDto);
    }

    @Override
    public Page<EpisodeDto> getAllByCollectionId(Long collectionId, Pageable pageable) {
        Page<EpisodeEntity> episodeEntities = repository.findAllByCollectionEntityId(collectionId, pageable);
        return new PageImpl<>(
                episodeEntities.stream()
                               .map(EpisodeDto::compressedFrom)
                               .collect(Collectors.toList()),
                pageable,
                episodeEntities.getTotalElements()
        );
    }


    @Override
    public Integer getStatistic(Long id, Long userId) {
        UserSettingDto userSettingDto = userSettingInfraService.getByUserId(userId);
        return repository.getLearnedPercent(id, userId, userSettingDto.getLearnedWordCount());
    }

    @Override
    public Page<Integer> getSeasonsByCollectionId(Long collectionId, Pageable pageable) {
        return repository.getSeasonsByCollectionId(collectionId, pageable);
    }

    @Override
    public Page<EpisodeDto> getAllByCollectionIdAndSeason(Long collectionId, Integer season, Pageable pageable) {
        Page<EpisodeEntity> episodeEntities = repository.findAllBySeasonAndCollectionEntityId(
                season,
                collectionId,
                pageable
        );
        return new PageImpl<>(episodeEntities
                                      .stream()
                                      .map(EpisodeDto::compressedFrom)
                                      .collect(Collectors.toList()), pageable, episodeEntities.getTotalElements());
    }

    @Override
    public EpisodeDto getByCollectionIdAndSeasonAndSeries(Long collectionId, Integer season, Integer series) {
        EpisodeEntity episodeEntity = repository.findBySeasonAndEpisodeAndCollectionEntityId(
                season,
                series,
                collectionId
        );
        return EpisodeDto.compressedFrom(episodeEntity);

    }

    @Override
    public Boolean isEmptyEpisode(Long id) {
        return repository.isEmptyWords(id);
    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
