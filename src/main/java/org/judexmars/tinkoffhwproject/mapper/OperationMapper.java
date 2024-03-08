package org.judexmars.tinkoffhwproject.mapper;

import org.judexmars.tinkoffhwproject.dto.operation.OperationDto;
import org.judexmars.tinkoffhwproject.model.OperationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationMapper {
    List<OperationDto> operationsToOperationDtos(List<OperationEntity> allByType);

    @Mapping(target = "id", expression = "java(null)")
    OperationEntity operationDtoToOperation(OperationDto operationDto);
}
