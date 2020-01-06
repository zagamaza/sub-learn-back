package ru.zagamaza.sublearn.controller;

import lombok.RequiredArgsConstructor;
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
import ru.zagamaza.sublearn.dto.TrialWordDto;
import ru.zagamaza.sublearn.dto.TrialWordRequest;
import ru.zagamaza.sublearn.infra.service.TrialWordInfraService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/trial_words")
@RequiredArgsConstructor
public class TrialWordController {

    private final TrialWordInfraService service;

    @GetMapping("/{id}")
    public TrialWordDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public TrialWordDto save(@Valid @RequestBody TrialWordRequest trialWordRequest) {
        TrialWordDto trialWordDto = TrialWordDto.from(trialWordRequest);
        return service.save(trialWordDto);
    }

    @PutMapping
    public TrialWordDto update(@Valid @RequestBody TrialWordRequest trialWordRequest) {
        TrialWordDto trialWordDto = TrialWordDto.from(trialWordRequest);
        return service.update(trialWordDto);
    }

    @PutMapping("/user_word")
    public TrialWordDto updateTrialWordAndSaveUserWord(@Valid @RequestBody TrialWordRequest trialWordRequest) {
        TrialWordDto trialWordDto = TrialWordDto.from(trialWordRequest);
        return service.updateTrialWordAndSaveUserWord(trialWordDto);
    }

    @GetMapping("/{id}/learned")
    public TrialWordDto updateTrialWordAndSaveLearnedUserWord(
            @PathVariable("id") Long id,
            @RequestParam Boolean isRight
    ) {
        return service.updateTrialWordAndSaveLearnedUserWord(id, isRight);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }

}
