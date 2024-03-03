package org.judexmars.tinkoffhwproject.controller;

import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.MessageDto;
import org.judexmars.tinkoffhwproject.service.MessageService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageGraphQLController {

    private final MessageService messageService;

    @QueryMapping
    public List<MessageDto> getAllMessages() {
        return messageService.getAllMessages();
    }

    @QueryMapping
    public MessageDto getMessage(@Argument long id) {
        return messageService.getMessageById(id);
    }

    @MutationMapping
    public MessageDto sendMessage(@Argument String author, @Argument String content) {
        return messageService.createMessage(author, content);
    }
}
