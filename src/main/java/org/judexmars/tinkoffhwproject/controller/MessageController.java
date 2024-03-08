package org.judexmars.tinkoffhwproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.image.ImageDto;
import org.judexmars.tinkoffhwproject.dto.message.MessageDto;
import org.judexmars.tinkoffhwproject.dto.message.SendMessageDto;
import org.judexmars.tinkoffhwproject.exception.ImagesNotFoundException;
import org.judexmars.tinkoffhwproject.service.MessageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@SecurityRequirement(name = "Auth")
@Tag(name = "message", description = "Работа с сообщениями")
public class MessageController {

    private final MessageService messageService;

    @Operation(description = "Получить все сообщения")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MessageDto.class))))
    })
    @GetMapping("/messages")
    @PreAuthorize("hasAuthority('GET_ALL_MESSAGES')")
    public List<MessageDto> getMessages() {
        return messageService.getAllMessages(); }

    @Operation(description = "Получить сообщение по id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAuthority('GET_MESSAGE')")
    @GetMapping("/message/{id}")
    public MessageDto getMessage(@PathVariable Long id) {
        return messageService.getMessageById(id);
    }

    @Operation(description = "Получить картинки сообщения по id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    array= @ArraySchema(schema = @Schema(implementation = ImageDto.class)))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAuthority('GET_MESSAGE')")
    @GetMapping("/messages/{id}/images")
    public List<ImageDto> getMessageImages(@PathVariable Long id) {
        return messageService.getMessageImages(id);
    }

    @Operation(description = "Отправить новое сообщение")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageDto.class))),
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PreAuthorize("hasAuthority('SEND_MESSAGE')")
    @PostMapping("/send")
    public MessageDto sendMessage(@RequestBody @Valid SendMessageDto messageDto) throws ImagesNotFoundException {
        return messageService.createMessage(messageDto, messageDto.imageIds());
    }
}
