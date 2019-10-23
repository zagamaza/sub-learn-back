package ru.zagamaza.sublearn.controller;

import io.swagger.annotations.ApiOperation;
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
import ru.zagamaza.sublearn.dto.TranslateOptionDto;
import ru.zagamaza.sublearn.dto.TrialCondensedDto;
import ru.zagamaza.sublearn.dto.TrialDto;
import ru.zagamaza.sublearn.dto.TrialRequest;
import ru.zagamaza.sublearn.infra.service.TrialInfraService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/trials")
@RequiredArgsConstructor
public class TrialController {

    private final TrialInfraService service;

    @GetMapping("/{id}")
    public TrialDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<TrialDto> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/condensed/users/{userId}")
    public List<TrialCondensedDto> getLastConsedTrial(@PathVariable Long userId, Pageable pageable) {
        return service.getLastConsedTrialByUserId(userId, pageable);
    }
    @GetMapping("/condensed/users/{userId}/count")
    public Integer getLastConsedTrial(@PathVariable Long userId) {
        return service.getCountTrialByUserId(userId);
    }

    @GetMapping("/nextWord")
    public TranslateOptionDto getTranslateOptionDto(@RequestParam Long trialId) {
        return service.getNextWord(trialId);
    }


    @ApiOperation(value = "Operation for save Trial and TrialWord with unused words from Episode")
    @PostMapping("/trial_word")
    public TrialDto saveTrialAndTrialWords(@Valid @RequestBody TrialRequest trialRequest) {
        TrialDto trialDto = TrialDto.from(trialRequest);
        return service.saveTrialAndTrialWords(trialDto);
    }

    @PostMapping
    public TrialDto create(@Valid @RequestBody TrialRequest trialRequest) {
        TrialDto trialDto = TrialDto.from(trialRequest);
        return service.save(trialDto);
    }

    @PutMapping
    public TrialDto update(@Valid @RequestBody TrialRequest trialRequest) {
        TrialDto trialDto = TrialDto.from(trialRequest);
        return service.saveTrialAndTrialWords(trialDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }

}
