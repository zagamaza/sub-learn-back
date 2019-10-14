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
import ru.zagamaza.sublearn.dto.CollectionCondensedDto;
import ru.zagamaza.sublearn.dto.CollectionDto;
import ru.zagamaza.sublearn.dto.CollectionRequest;
import ru.zagamaza.sublearn.infra.service.CollectionInfraService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionInfraService service;

    @GetMapping("/{id}")
    public CollectionDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/condensed/users/{userId}")
    public List<CollectionCondensedDto> getCollectionByUserId(@PathVariable Long userId, Pageable pageable) {
        return service.getCondensedCollectionByUserId(userId, pageable);
    }

    @GetMapping
    public List<CollectionCondensedDto> search(@RequestParam String collectionName, Pageable pageable) {
        return service.findByContainsName(collectionName, pageable);
    }

    @GetMapping("/{id}/users/{userId}/copy")
    public CollectionDto copy(@PathVariable Long id, @PathVariable Long userId) {
        return service.copyCollectionToUser(id, userId);
    }

    @PutMapping
    public CollectionDto update(@Valid @RequestBody CollectionRequest collectionRequest) {
        return service.update(CollectionDto.from(collectionRequest));
    }

    @PutMapping("/{id}")
    public CollectionDto updateIsSerial(@PathVariable Long id, @RequestParam Boolean isSerial) {
        return service.updateIsSerial(id, isSerial);
    }

    @PostMapping
    public CollectionDto create(@Valid @RequestBody CollectionRequest collectionRequest) {
        return service.save(CollectionDto.from(collectionRequest));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }

}
