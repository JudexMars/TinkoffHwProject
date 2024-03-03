package org.judexmars.tinkoffhwproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class UploadFailedException extends ResponseStatusException {

    private final String messageCode;

    public UploadFailedException() {
        super(HttpStatus.BAD_REQUEST);
        this.messageCode = "exception.upload_failed";
    }
}
