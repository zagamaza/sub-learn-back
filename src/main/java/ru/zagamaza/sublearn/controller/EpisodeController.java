package ru.zagamaza.sublearn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.zagamaza.sublearn.dto.EpisodeDto;
import ru.zagamaza.sublearn.dto.EpisodeRequest;
import ru.zagamaza.sublearn.infra.service.EpisodeInfraService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/episodes")
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeInfraService service;

    @GetMapping("/{id}")
    public EpisodeDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/collections/{collectionId}")
    public List<EpisodeDto> getAllByCollectionId(@PathVariable Long collectionId, Pageable pageable) {
        return service.getAllByCollectionId(collectionId, pageable);
    }

    @GetMapping("{id}/users/{userId}")
    public Integer getLearnedPercent(@PathVariable Long id, @PathVariable Long userId) {
        return service.getStatistic(id, userId);
    }

    @GetMapping("/collections/{collectionId}/count")
    public Integer getCountByCollectionId(@PathVariable Long collectionId) {
        return service.getCountByCollectionId(collectionId);
    }

    @GetMapping("/collections/{collectionId}/seasons")
    public List<Integer> getSeasonsByCollectionId(@PathVariable Long collectionId) {
        return service.getSeasonsByCollectionId(collectionId);
    }

    @GetMapping("/collections/{collectionId}/season")
    public List<EpisodeDto> getByCollectionIdAndSeason(
            @PathVariable Long collectionId,
            @RequestParam Integer season,
            Pageable pageable
    ) {
        return service.getAllByCollectionIdAndSeason(collectionId, season, pageable);
    }

    @GetMapping("/collections/{collectionId}/season/count")
    public Integer getCountByCollectionIdAndSeason(
            @PathVariable Long collectionId,
            @RequestParam Integer season
    ) {
        return service.getCountByCollectionIdAndSeason(collectionId, season);
    }

    @PostMapping
    public EpisodeDto create(@Valid @RequestBody EpisodeRequest episodeRequest) {
        EpisodeDto episodeDto = EpisodeDto.from(episodeRequest);
        return service.save(episodeDto);
    }

    @PutMapping("/{id}")
    public EpisodeDto addWordsInEpisode(@PathVariable Long id, @RequestParam MultipartFile file) {
        return service.parseAndSave(id, file);
    }

    @PutMapping
    public EpisodeDto update(@Valid @RequestBody EpisodeRequest episodeRequest) {
        EpisodeDto episodeDto = EpisodeDto.from(episodeRequest);
        return service.save(episodeDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }

}
