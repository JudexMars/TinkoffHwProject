package org.judexmars.tinkoffhwproject.exception;

public class ImagesNotFoundException extends BaseNotFoundException {

    public ImagesNotFoundException() {
        super("exception.images_not_found", (Object) null);
    }
}
