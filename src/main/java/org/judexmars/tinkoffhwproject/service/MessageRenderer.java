package org.judexmars.tinkoffhwproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageRenderer {

    private final MessageSource messageSource;

    public String render(String messageCode, Object ... parameters) {
        return messageSource.getMessage(messageCode, parameters, Locale.getDefault());
    }
}
