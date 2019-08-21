package ru.zagamaza.sublearn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.zagamaza.sublearn.controller.presenter.ErrorPresenter;
import ru.zagamaza.sublearn.domain.exception.DomainException;
import ru.zagamaza.sublearn.dto.ErrorResponseDto;

import java.util.Locale;
import java.util.UUID;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestResponseExceptionHandler {

    private final MessageSource messageSource;
    private final ErrorPresenter presenter;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handle(MethodArgumentNotValidException e, Locale locale) {
        var errorId = UUID.randomUUID().toString();
        var bindingResult = e.getBindingResult();
        var bindingMessage = messageSource.getMessage("api.exception.binding.message", null, locale);

        var errors = bindingResult.getFieldErrors().stream()
                                  .map(o -> new ErrorResponseDto.ErrorDto(o.getField(), o.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(presenter.getErrorResponse(errorId, HttpStatus.BAD_REQUEST, bindingMessage));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponseDto> handle(DomainException e) {
        var errorId = e.getUuid();

        return ResponseEntity
                .status(e.getHttpStatus())
                .body(presenter.getErrorResponse(errorId, HttpStatus.BAD_REQUEST, e.getMessage()));
    }

}
