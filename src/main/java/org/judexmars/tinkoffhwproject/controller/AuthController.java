package org.judexmars.tinkoffhwproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.account.CreateAccountDto;
import org.judexmars.tinkoffhwproject.dto.auth.JwtRefreshRequestDto;
import org.judexmars.tinkoffhwproject.dto.auth.JwtRequestDto;
import org.judexmars.tinkoffhwproject.dto.auth.JwtResponseDto;
import org.judexmars.tinkoffhwproject.exception.handler.AppExceptionHandler;
import org.judexmars.tinkoffhwproject.model.AccountEntity;
import org.judexmars.tinkoffhwproject.service.AccountService;
import org.judexmars.tinkoffhwproject.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "auth", description = "Аутентификация")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;

    @Operation(description = "Вход в аккаунт")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Вход успешен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректный формат данных", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AppExceptionHandler.ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "403", description = "Некорректные реквизиты", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AppExceptionHandler.ErrorResponse.class))
            })
    })
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody @Valid JwtRequestDto requestDto) {
        var userDetails = accountService.loadUserByUsername(requestDto.username());
        JwtResponseDto jwtResponseDto = getResponseDto((AccountEntity) userDetails, requestDto.username(), requestDto.password());
        return ResponseEntity.ok(jwtResponseDto);
    }

    @Operation(description = "Регистрация аккаунта")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Регистрация успешна", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректный формат данных", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AppExceptionHandler.ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "403", description = "Некорректные реквизиты", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AppExceptionHandler.ErrorResponse.class))
            })
    })
    @PostMapping("/signup")
    public ResponseEntity<JwtResponseDto> signUp(@RequestBody @Valid CreateAccountDto requestDto) {
        var createdAccountDto = accountService.createAccount(requestDto);
        var userDetails = accountService.loadUserByUsername(createdAccountDto.username());
        var jwtResponseDto = getResponseDto((AccountEntity) userDetails, requestDto.username(), requestDto.password());
        return ResponseEntity.ok(jwtResponseDto);
    }

    @Operation(description = "Обновление токена")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Создан новый токен", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Некорректный формат данных", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AppExceptionHandler.ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "403", description = "Некорректные реквизиты", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AppExceptionHandler.ErrorResponse.class))
            })
    })
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDto> refresh(@RequestBody @Valid JwtRefreshRequestDto requestDto) {
        var newTokens = authService.refresh(requestDto.token());
        return ResponseEntity.ok(new JwtResponseDto(
                Long.valueOf(newTokens[2]),
                newTokens[3],
                newTokens[0],
                newTokens[1]
        ));
    }

    private JwtResponseDto getResponseDto(AccountEntity account, String name, String password) {
        var tokens = authService.createAuthTokens(account, name, password);
        return new JwtResponseDto(
                account.getId(),
                account.getUsername(),
                tokens[0],
                tokens[1]
        );
    }
}
