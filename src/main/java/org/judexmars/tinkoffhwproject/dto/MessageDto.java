package org.judexmars.tinkoffhwproject.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;

public record MessageDto(
        @NotBlank
        String author,
        @NotBlank
        String content,
        LocalDateTime lastModifiedDate) implements Serializable {
}
