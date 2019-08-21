package ru.zagamaza.sublearn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto {

    private String message;
    private String uuid;
    private List<ErrorDto> errors = new LinkedList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDto {

        private String field;
        private String message;

    }

}
