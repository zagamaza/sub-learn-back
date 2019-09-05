package ru.zagamaza.sublearn.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.zagamaza.sublearn.domain.service.EpisodeService;
import ru.zagamaza.sublearn.dto.EpisodeDto;
import ru.zagamaza.sublearn.util.SubUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EpisodeServiceImpl implements EpisodeService {

    @Override
    public EpisodeDto updateEpisode(EpisodeDto saveDto, EpisodeDto updateDto) {
        saveDto.setSeason(updateDto.getSeason());
        saveDto.setEpisode(updateDto.getEpisode());
        return saveDto;
    }

    @Override
    public List<String> parseWords(MultipartFile file) {
        String text = "";
        try {
            text = IOUtils.toString(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SubUtil.getWords(text);
    }

}
