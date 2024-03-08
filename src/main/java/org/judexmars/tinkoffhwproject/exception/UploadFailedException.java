package org.judexmars.tinkoffhwproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UploadFailedException extends BaseException {

    public UploadFailedException() {
        super(HttpStatus.BAD_REQUEST, "exception.upload_failed");
    }
}
