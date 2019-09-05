package ru.zagamaza.sublearn.infra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.exception.domain.NotFoundException;
import ru.zagamaza.sublearn.infra.dao.entity.WordEntity;
import ru.zagamaza.sublearn.infra.dao.repository.WorldRepository;
import ru.zagamaza.sublearn.infra.service.WordInfraService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordInfraServiceImpl implements WordInfraService {

    private final WorldRepository repository;
    private final MessageSource messageSource;

    @Override
    public WordDto get(Long id) {
        WordEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(getMessage("word.not.found.exception", id)));
        return WordDto.from(entity);
    }

    @Override
    @Transactional
    public WordDto save(WordDto dto) {
        WordEntity wordEntity = repository.findByWord(dto.getWord()).orElse(WordEntity.from(dto));
        wordEntity = repository.save(wordEntity);
        return WordDto.from(wordEntity);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        repository.deleteById(id);
    }

    @Override
    public WordDto getByName(String name) {
        WordEntity entity = repository
                .findByWord(name)
                .orElseThrow(() -> new NotFoundException(getMessage("word.not.found.exception", name)));

        return WordDto.from(entity);
    }

    @Override
    public List<WordDto> getRandomWordsByEpisodeId(Long episodeId, Integer countWord) {
        List<Long> wordIds = repository.findRandomWordsByEpisodeId(episodeId, countWord);
        return wordIds.stream()
                      .map(this::get)
                      .collect(Collectors.toList());
    }

    @Override
    public List<String> removeWordsAlreadySave(List<String> words, List<WordDto> wordDtos) {
        words = words.stream()
                     .filter(word -> {
                         try {
                             WordDto wordDto = getByName(word);
                             if (wordDto != null) {
                                 wordDtos.add(wordDto);
                                 return false;
                             }
                         } catch (Exception ignored) {}
                         return true;
                     }).collect(Collectors.toList());
        return words;
    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

}
