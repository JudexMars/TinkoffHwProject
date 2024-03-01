package org.judexmars.tinkoffhwproject.mapper;

import org.judexmars.tinkoffhwproject.dto.OperationDto;
import org.judexmars.tinkoffhwproject.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationMapper {
    List<OperationDto> operationsToOperationDtos(List<Operation> allByType);

    @Mapping(target = "id", expression = "java(null)")
    Operation operationDtoToOperation(OperationDto operationDto);
}
