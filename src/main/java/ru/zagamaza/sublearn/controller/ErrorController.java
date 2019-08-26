package ru.zagamaza.sublearn.controller;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import ru.zagamaza.sublearn.domain.exception.DomainException;
import ru.zagamaza.sublearn.dto.ErrorResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends AbstractErrorController {

    private final ErrorAttributes errorAttributes;
    private final MessageSource messageSource;

    public ErrorController(ErrorAttributes errorAttributes, MessageSource messageSource) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
        this.messageSource = messageSource;
    }

    @RequestMapping
    public ResponseEntity<ErrorResponse> error(HttpServletRequest request, Locale locale) {
        var webRequest = new ServletWebRequest(request);
        var throwable = errorAttributes.getError(webRequest);
        var status = getStatus(request);
        var defaultMessage = messageSource.getMessage("api.exception.default.message", null, locale);

        if (throwable == null) {
            return ResponseEntity
                    .status(status)
                    .body(new ErrorResponse(defaultMessage, UUID.randomUUID().toString(), Collections.emptyList()));
        }

        if (throwable instanceof DomainException) {
            return ResponseEntity.status(status).body(
                    new ErrorResponse(
                            messageSource.getMessage(throwable.getMessage(), null, defaultMessage, locale),
                            UUID.randomUUID().toString(),
                            Collections.emptyList()
                    )
            );
        }

        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(defaultMessage, UUID.randomUUID().toString(), Collections.emptyList()));
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
