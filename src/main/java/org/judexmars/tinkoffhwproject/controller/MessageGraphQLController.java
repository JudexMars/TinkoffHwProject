package org.judexmars.tinkoffhwproject.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.message.MessageDto;
import org.judexmars.tinkoffhwproject.service.MessageService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@SecurityRequirement(name = "Auth")
@RequiredArgsConstructor
public class MessageGraphQLController {

    private final MessageService messageService;

    @QueryMapping
    @PreAuthorize("hasAuthority('GET_ALL_MESSAGES')")
    public List<MessageDto> getAllMessages() {
        return messageService.getAllMessages();
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('GET_MESSAGE')")
    public MessageDto getMessage(@Argument long id) {
        return messageService.getMessageById(id);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('SEND_MESSAGE')")
    public MessageDto sendMessage(@Argument String author, @Argument String content) {
        return messageService.createMessage(author, content);
    }
}
