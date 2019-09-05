package ru.zagamaza.sublearn.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.zagamaza.sublearn.domain.service.CollectionService;
import ru.zagamaza.sublearn.dto.CollectionDto;
import ru.zagamaza.sublearn.exception.domain.NotFoundException;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final MessageSource messageSource;

    @Override
    public CollectionDto updateCollection(CollectionDto saveDto, CollectionDto updateDto) {
        saveDto.setCreated(LocalDateTime.now());
        saveDto.setLang(updateDto.getLang());
        saveDto.setName(updateDto.getName());
        saveDto.setSerial(updateDto.isSerial());
        return saveDto;
    }

    private String getMessage(String key, Object... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }


}
