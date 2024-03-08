package org.judexmars.tinkoffhwproject.exception;

import org.springframework.http.HttpStatus;

public class InvalidJwtException extends BaseException {

    public InvalidJwtException() {
        super(HttpStatus.UNAUTHORIZED, "exception.invalid_jwt");
    }
}
