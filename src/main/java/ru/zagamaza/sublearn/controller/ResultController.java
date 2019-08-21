package ru.zagamaza.sublearn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.zagamaza.sublearn.dto.ResultRequestDto;
import ru.zagamaza.sublearn.dto.ResultDto;
import ru.zagamaza.sublearn.infra.service.api.ResultInfraService;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultInfraService resultInfraService;

    @GetMapping("/{id}")
    public ResultDto get(@PathVariable Long id) {
        return resultInfraService.get(id);
    }

    @GetMapping("/users/{id}")
    public ResultDto getLastResultByUserId(@PathVariable Long id) {
        return resultInfraService.get(id);
    }

    @PostMapping
    public ResultDto save(@RequestBody ResultRequestDto resultRequestDto) {
        ResultDto resultDto = ResultDto.from(resultRequestDto);
        return resultInfraService.save(resultDto);
    }


}
