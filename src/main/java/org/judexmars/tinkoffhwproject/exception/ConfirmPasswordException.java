package org.judexmars.tinkoffhwproject.exception;

import org.springframework.http.HttpStatus;

public class ConfirmPasswordException extends BaseException {

    public ConfirmPasswordException() {
        super(HttpStatus.BAD_REQUEST, "exception.confirm_password_invalid");
    }
}
