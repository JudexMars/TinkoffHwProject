package org.judexmars.tinkoffhwproject.dto.account;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountDto(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        String confirmPassword
) {
}
