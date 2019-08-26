package ru.zagamaza.sublearn.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.zagamaza.sublearn.controller.presenter.ErrorPresenter;
import ru.zagamaza.sublearn.domain.exception.DomainException;
import ru.zagamaza.sublearn.dto.ErrorResponse;

import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestResponseExceptionHandler {

    private final MessageSource messageSource;
    private final ErrorPresenter presenter;

    private static final Logger logger = LoggerFactory.getLogger(RestResponseExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e, Locale locale) {
        var errorId = UUID.randomUUID().toString();
        var bindingResult = e.getBindingResult();
        var bindingMessage = messageSource.getMessage("api.exception.binding.message", null, locale);

        var errors = bindingResult.getFieldErrors().stream()
                                  .map(o -> new ErrorResponse.ErrorDto(o.getField(), o.getDefaultMessage()))
                                  .collect(Collectors.toList());

        logger.error("Error {} {} {}", errorId, bindingMessage, errors, e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(bindingMessage, errorId, errors));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handle(DomainException e) {
        logger.error("Error {} {}", e.getUuid(), e.getMessage(), e);
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(presenter.getErrorResponse(e.getUuid(), e.getHttpStatus(), e.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        logger.error("Error {}", e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(presenter.getErrorResponse(UUID.randomUUID().toString(), HttpStatus.BAD_REQUEST, e.getMessage()));
    }

}
