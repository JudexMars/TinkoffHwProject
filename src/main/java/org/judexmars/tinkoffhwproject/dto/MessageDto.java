package org.judexmars.tinkoffhwproject.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record MessageDto(
        String author,
        String content,
        LocalDateTime lastModifiedDate) implements Serializable {
}
