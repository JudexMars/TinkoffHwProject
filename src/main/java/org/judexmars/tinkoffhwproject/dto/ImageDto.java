package org.judexmars.tinkoffhwproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

public record ImageDto (
    @NotBlank
    String name,
    @NotNull
    @Positive
    Long size,
    @NotBlank
    String fileId
) implements Serializable {
}
