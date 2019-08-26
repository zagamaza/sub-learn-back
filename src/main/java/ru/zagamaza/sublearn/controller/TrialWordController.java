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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.zagamaza.sublearn.dto.TrialWordDto;
import ru.zagamaza.sublearn.dto.TrialWordRequest;
import ru.zagamaza.sublearn.infra.service.api.TrialWordInfraService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/trial_word")
@RequiredArgsConstructor
public class TrialWordController {

    private final TrialWordInfraService service;

    @GetMapping("/{id}")
    public TrialWordDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/users/{id}")
    public TrialWordDto getLastResultByUserId(@PathVariable Long id) {
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
        return service.save(trialWordDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }

}
