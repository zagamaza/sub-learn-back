package ru.zagamaza.sublearn.controller.presenter;

import org.springframework.http.HttpStatus;
import ru.zagamaza.sublearn.dto.ErrorResponseDto;

public interface ErrorPresenter {

    ErrorResponseDto getErrorResponse(String uuid, HttpStatus httpStatus, String message);

}
