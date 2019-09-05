package ru.zagamaza.sublearn.exception.infra;

import org.springframework.http.HttpStatus;
import ru.zagamaza.sublearn.exception.BaseException;

public class InfraException extends BaseException {


    public InfraException() {
        super();
    }

    public InfraException(String message) {
        super(message);
    }

    public InfraException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfraException(Throwable cause) {
        super(cause);
    }

    protected InfraException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}