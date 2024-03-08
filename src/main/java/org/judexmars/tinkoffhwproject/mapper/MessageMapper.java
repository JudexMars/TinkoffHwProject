package org.judexmars.tinkoffhwproject.mapper;

import org.judexmars.tinkoffhwproject.dto.message.MessageDto;
import org.judexmars.tinkoffhwproject.dto.message.SendMessageDto;
import org.judexmars.tinkoffhwproject.model.MessageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDto MessageToMessageDto(MessageEntity message);

    MessageEntity sendMessageDtoToMessage(SendMessageDto messageDto);
}
