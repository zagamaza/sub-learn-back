package ru.zagamaza.sublearn.controller.presenter;

import org.springframework.http.HttpStatus;
import ru.zagamaza.sublearn.dto.ErrorResponse;

public interface ErrorPresenter {

    ErrorResponse getErrorResponse(String uuid, HttpStatus httpStatus, String message);

}
