package ru.zagamaza.sublearn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.zagamaza.sublearn.dto.WordDto;
import ru.zagamaza.sublearn.infra.dao.entity.Lang;

import java.util.List;

@FeignClient(contextId = "wordTranslator", value = "wordTranslator", url = "${translator.url}")
public interface TranslatorClient {

    @PostMapping
    List<WordDto> translate(@RequestBody List<String> text, @RequestParam("lang") Lang lang);

    @PostMapping("/dictionary")
    List<WordDto> translateWithDictionaryAll(@RequestBody List<String> text, @RequestParam("lang") Lang lang);

    @GetMapping
    WordDto translate(@RequestParam("word") String word, @RequestParam("lang") Lang lang);

}
