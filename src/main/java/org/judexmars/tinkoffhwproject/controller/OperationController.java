package org.judexmars.tinkoffhwproject.controller;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.operation.OperationDto;
import org.judexmars.tinkoffhwproject.model.OperationEntity;
import org.judexmars.tinkoffhwproject.service.OperationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@SecurityRequirement(name = "Auth")
@Tag(name = "Операции (логирование)")
public class OperationController {

    private final OperationService service;

    @io.swagger.v3.oas.annotations.Operation(description = "Получить операции выбранного типа")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = OperationDto.class))))
    })
    @PreAuthorize("hasAuthority('AUDIT_OPERATION')")
    @GetMapping("/operation/{type}")
    public List<OperationDto> getOperations(@PathVariable OperationEntity.OperationType type) {
        return service.getOperationsByType(type);
    }
}
