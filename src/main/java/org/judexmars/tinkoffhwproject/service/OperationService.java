package org.judexmars.tinkoffhwproject.service;

import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.OperationDto;
import org.judexmars.tinkoffhwproject.mapper.OperationMapper;
import org.judexmars.tinkoffhwproject.model.Operation;
import org.judexmars.tinkoffhwproject.repository.OperationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository repository;

    private final OperationMapper mapper;

    public List<OperationDto> getOperationsByType(Operation.OperationType type) {
        return mapper.operationsToOperationDtos(repository.findAllByType(type));
    }

    public void logOperation(OperationDto operationDto) {
        repository.save(mapper.operationDtoToOperation(operationDto));
    }
}
