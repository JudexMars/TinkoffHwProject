package org.judexmars.tinkoffhwproject.dto.operation;

import org.judexmars.tinkoffhwproject.model.OperationEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

public record OperationDto(
        String content,

        LocalDateTime timestamp,
        OperationEntity.OperationType type
) implements Serializable {
}
