package org.judexmars.tinkoffhwproject.controller;

import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.OperationDto;
import org.judexmars.tinkoffhwproject.model.Operation;
import org.judexmars.tinkoffhwproject.service.OperationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService service;

    @GetMapping("/operation/{type}")
    public List<OperationDto> getOperations(@PathVariable Operation.OperationType type) {
        return service.getOperationsByType(type);
    }
}
