package ru.zagamaza.sublearn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zagamaza.sublearn.dto.CollectionCondensedDto;
import ru.zagamaza.sublearn.dto.CollectionDto;
import ru.zagamaza.sublearn.infra.service.api.CollectionInfraService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionInfraService collectionInfraService;

    @GetMapping("/{id}")
    public CollectionDto get(@PathVariable Long id) {
        return collectionInfraService.get(id);
    }

    @GetMapping("/condensed/users/{userId}")
    public List<CollectionCondensedDto> getCollectionByUserId(@PathVariable Long userId) {
        return collectionInfraService.getCondensedCollectionByUserId(userId, PageRequest.of(0, 10));
    }

    @PostMapping
    public CollectionDto save(@Valid @RequestBody CollectionDto collectionDto) {
        return collectionInfraService.save(collectionDto);
    }

}
