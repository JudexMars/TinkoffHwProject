package org.judexmars.tinkoffhwproject.mapper;

import org.judexmars.tinkoffhwproject.dto.MessageDto;
import org.judexmars.tinkoffhwproject.dto.SendMessageDto;
import org.judexmars.tinkoffhwproject.model.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDto MessageToMessageDto(Message message);

    Message sendMessageDtoToMessage(SendMessageDto messageDto);
}
