package ru.zagamaza.sublearn.controller.presenter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.zagamaza.sublearn.dto.ErrorResponseDto;

@Component
@RequiredArgsConstructor
public class ErrorPresenterImpl implements ErrorPresenter {

    @Override

    public ErrorResponseDto getErrorResponse(
            String uuid, HttpStatus httpStatus, String message
    ) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setUuid(uuid);
        errorResponseDto.setMessage(message);
        return errorResponseDto;
    }

}
