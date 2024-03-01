package org.judexmars.tinkoffhwproject.config;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

@Configuration
public class LocaleConfiguration {

    public LocaleConfiguration(WebProperties webProperties) {
        Locale locale = webProperties.getLocale();
        if (locale == null) {
            locale = new Locale("ru");
        }

        LocaleContextHolder.setLocale(locale);
        Locale.setDefault(locale);
    }
}
