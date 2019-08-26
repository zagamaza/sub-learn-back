package ru.zagamaza.sublearn.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
import ru.zagamaza.sublearn.dto.CollectionCondensedDto;
import ru.zagamaza.sublearn.dto.CollectionDto;
import ru.zagamaza.sublearn.dto.CollectionRequest;
import ru.zagamaza.sublearn.infra.service.api.CollectionInfraService;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Collection", description = "REST Collection API")
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
    public List<CollectionCondensedDto> getCollectionByUserId(@PathVariable Long userId) {
        return service.getCondensedCollectionByUserId(userId, PageRequest.of(0, 10));
    }

    @PutMapping
    public CollectionDto update(@Valid @RequestBody CollectionRequest collectionRequest) {
        return service.update(CollectionDto.from(collectionRequest));
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
