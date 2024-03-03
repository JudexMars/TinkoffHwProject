package org.judexmars.tinkoffhwproject.service;

import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.ImageDto;
import org.judexmars.tinkoffhwproject.dto.MessageDto;
import org.judexmars.tinkoffhwproject.dto.OperationDto;
import org.judexmars.tinkoffhwproject.exception.ImagesNotFoundException;
import org.judexmars.tinkoffhwproject.exception.MessageNotFoundException;
import org.judexmars.tinkoffhwproject.mapper.ImageMapper;
import org.judexmars.tinkoffhwproject.mapper.MessageMapper;
import org.judexmars.tinkoffhwproject.model.Message;
import org.judexmars.tinkoffhwproject.model.Operation;
import org.judexmars.tinkoffhwproject.repository.MessageRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final OperationService operationService;

    private final ImageService imageService;

    private final MessageMapper messageMapper;
    private final ImageMapper imageMapper;

    public List<MessageDto> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(messageMapper::MessageToMessageDto).collect(Collectors.toList());
    }

    @Cacheable(value = "MessageService::getMessageById", key = "#id")
    public MessageDto getMessageById(Long id) {
        var message = messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(String.valueOf(id)));
        operationService.logOperation(
                new OperationDto(
                        String.format("Read message: %s", message),
                        LocalDateTime.now(),
                        Operation.OperationType.READ
                )
        );
        return messageMapper.MessageToMessageDto(message);
    }

    public MessageDto createMessage(Message message, List<Long> imagesIds) throws ImagesNotFoundException {
        if (imagesIds == null) {imagesIds = List.of();}
        if (!imagesIds.isEmpty() && !imageService.existAll(imagesIds)) {
            throw new ImagesNotFoundException();
        }
        message.setImages(imageService.getAllImages(imagesIds));
        message = messageRepository.save(message);

        operationService.logOperation(
                new OperationDto(
                        String.format("Send message: %s", message),
                        LocalDateTime.now(),
                        Operation.OperationType.WRITE
                )
        );
        return messageMapper.MessageToMessageDto(message);
    }

    public MessageDto createMessage(String author, String content) {
        return messageMapper.MessageToMessageDto(messageRepository
                .save(Message.builder().author(author).content(content).build()));
    }

    @Cacheable(value = "MessageService::getMessageImages", key = "#id + '.images'")
    public List<ImageDto> getMessageImages(Long id) {
        var message = messageRepository.findById(id).orElseThrow(() -> new MessageNotFoundException(String.valueOf(id)));
        operationService.logOperation(
                new OperationDto(
                        String.format("Read message images: %s", message),
                        LocalDateTime.now(),
                        Operation.OperationType.READ
                )
        );
        return message.getImages().stream().map(imageMapper::imageToImageDto).collect(Collectors.toList());
    }
}
