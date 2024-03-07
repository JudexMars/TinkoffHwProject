package org.judexmars.tinkoffhwproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class BaseNotFoundException extends ResponseStatusException {

    private final String messageCode;

    private final Object[] args;

    public BaseNotFoundException(String messageCode, Object ... args) {
        super(HttpStatus.NOT_FOUND);
        this.messageCode = messageCode;
        this.args = args;
    }

}
