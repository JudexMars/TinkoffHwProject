package org.judexmars.tinkoffhwproject.dto;

import org.judexmars.tinkoffhwproject.model.Operation;

import java.io.Serializable;
import java.time.LocalDateTime;

public record OperationDto(
        String content,

        LocalDateTime timestamp,
        Operation.OperationType type
) implements Serializable {
}
