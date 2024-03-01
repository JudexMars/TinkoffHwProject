package org.judexmars.tinkoffhwproject.exception;

public class MessageNotFoundException extends BaseNotFoundException {
    public MessageNotFoundException(String messageCode, Object... args) {
        super("exception.message.not_found", args);
    }
}
