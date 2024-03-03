package org.judexmars.tinkoffhwproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.ImageDto;
import org.judexmars.tinkoffhwproject.exception.ImageNotFoundException;
import org.judexmars.tinkoffhwproject.exception.UploadFailedException;
import org.judexmars.tinkoffhwproject.exception.handler.AppExceptionHandler;
import org.judexmars.tinkoffhwproject.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "image", description = "Работа с картинками")
public class ImageController {

    private final ImageService service;

    @Operation(description = "Загрузить новую картинку")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ImageDto.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = AppExceptionHandler.ErrorResponse.class)))
    })
    @PostMapping("/load")
    public ImageDto loadImage(MultipartFile file) throws UploadFailedException {
        return service.uploadImage(file);
    }

    @Operation(description = "Получить картинку по ссылке")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = AppExceptionHandler.ErrorResponse.class)))
    })
    @GetMapping(value = "/image/{link}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable String link) throws Exception {
        return service.downloadImage(link);
    }

    @Operation(description = "Получить метаданные картинки по id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ImageDto.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = AppExceptionHandler.ErrorResponse.class)))
    })
    @GetMapping("/image/{id}/meta")
    public ImageDto getMeta(@PathVariable int id) throws ImageNotFoundException { return service.getImageMeta(id); }
}
