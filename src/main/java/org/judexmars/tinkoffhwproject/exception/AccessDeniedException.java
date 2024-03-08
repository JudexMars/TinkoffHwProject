package org.judexmars.tinkoffhwproject.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends BaseException {

    public AccessDeniedException(Object... args) {
        super(HttpStatus.FORBIDDEN, "exception.access_denied", args);
    }
}
