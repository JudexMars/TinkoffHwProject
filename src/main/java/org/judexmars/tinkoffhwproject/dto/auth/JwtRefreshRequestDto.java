package org.judexmars.tinkoffhwproject.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record JwtRefreshRequestDto(
        @NotBlank
        String token
) {
}
