package ru.zagamaza.sublearn.controller.presenter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.zagamaza.sublearn.dto.ErrorResponse;

@Component
@RequiredArgsConstructor
public class ErrorPresenterImpl implements ErrorPresenter {

    @Override

    public ErrorResponse getErrorResponse(
            String uuid, HttpStatus httpStatus, String message
    ) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setUuid(uuid);
        errorResponse.setMessage(message);
        return errorResponse;
    }

}
