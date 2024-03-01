package org.judexmars.tinkoffhwproject.controller;

import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.ImageDto;
import org.judexmars.tinkoffhwproject.dto.MessageDto;
import org.judexmars.tinkoffhwproject.dto.SendMessageDto;
import org.judexmars.tinkoffhwproject.exception.ImagesNotFoundException;
import org.judexmars.tinkoffhwproject.mapper.ImageMapper;
import org.judexmars.tinkoffhwproject.mapper.MessageMapper;
import org.judexmars.tinkoffhwproject.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final ImageMapper imageMapper;

    @GetMapping("/messages")
    public List<MessageDto> getMessages() {
        return messageService.getAllMessages()
                .stream().map(messageMapper::MessageToMessageDto).collect(Collectors.toList()); }

    @GetMapping("/message/{id}")
    public MessageDto getMessage(@PathVariable Long id) {
        return messageMapper.MessageToMessageDto(messageService.getMessageById(id));
    }

    @GetMapping("/messages/{id}/images")
    public List<ImageDto> getMessageImages(@PathVariable Long id) {
        return messageService.getMessageImages(id)
                .stream()
                .map(imageMapper::imageToImageDto).collect(Collectors.toList());
    }

    @PostMapping("/send")
    public MessageDto sendMessage(@RequestBody SendMessageDto messageDto) throws ImagesNotFoundException {
        return messageMapper.MessageToMessageDto(messageService
                .createMessage(messageMapper.sendMessageDtoToMessage(messageDto), messageDto.imageIds()));
    }
}
