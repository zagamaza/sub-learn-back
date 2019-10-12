package ru.zagamaza.sublearn.domain.service;

import org.springframework.web.multipart.MultipartFile;
import ru.zagamaza.sublearn.dto.EpisodeDto;
import ru.zagamaza.sublearn.dto.WordDto;

import java.util.List;

public interface EpisodeService {

    EpisodeDto updateEpisode(EpisodeDto saveDto, EpisodeDto updateDto);

    List<String> parseWords(MultipartFile file);

}
