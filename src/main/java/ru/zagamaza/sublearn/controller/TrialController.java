package ru.zagamaza.sublearn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.zagamaza.sublearn.dto.TranslateOptionDto;
import ru.zagamaza.sublearn.dto.TrialCondensedDto;
import ru.zagamaza.sublearn.dto.TrialDto;
import ru.zagamaza.sublearn.dto.TrialRequestDto;
import ru.zagamaza.sublearn.infra.service.api.TrialInfraService;

import java.util.List;

@RestController
@RequestMapping("/api/trials")
@RequiredArgsConstructor
public class TrialController {

    private final TrialInfraService trialInfraService;

    @PostMapping
    public TrialDto save(@RequestBody TrialRequestDto trialRequestDto) {
        TrialDto trialDto = TrialDto.from(trialRequestDto);
        return trialInfraService.save(trialDto);
    }

    @GetMapping("/{id}")
    public TrialDto get(@PathVariable Long id) {
        return trialInfraService.get(id);
    }

    @GetMapping("/condensed/users/{userId}")
    public List<TrialCondensedDto> getLastConsedTrial(@PathVariable Long userId) {
        return trialInfraService.getLastConsedTrialByUserId(userId, PageRequest.of(0, 10));
    }

    @GetMapping("/nextWord")
    public TranslateOptionDto getTranslateOptionDto(@RequestParam Long trialId) {
        return trialInfraService.getNextWord(trialId);
    }

}
