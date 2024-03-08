package org.judexmars.tinkoffhwproject.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record JwtRequestDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
