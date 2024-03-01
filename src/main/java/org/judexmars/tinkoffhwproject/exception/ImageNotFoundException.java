package org.judexmars.tinkoffhwproject.exception;

public class ImageNotFoundException extends BaseNotFoundException {
    public ImageNotFoundException(String link) {
        super("Image not found, link: " + link);
    }

    public ImageNotFoundException(Long id) {
        super("Image not found, id: " + id);
    }
}
