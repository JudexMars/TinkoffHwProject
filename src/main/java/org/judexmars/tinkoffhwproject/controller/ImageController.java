package org.judexmars.tinkoffhwproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.ImageDto;
import org.judexmars.tinkoffhwproject.exception.ImageNotFoundException;
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
    @PostMapping("/load")
    public ImageDto loadImage(MultipartFile file) throws Exception {
        return service.uploadImage(file);
    }

    @Operation(description = "Получить картинку по ссылке")
    @GetMapping(value = "/image/{link}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable String link) throws Exception {
        return service.downloadImage(link);
    }

    @Operation(description = "Получить метаданные картинки по id")
    @GetMapping("/image/{id}/meta")
    public ImageDto getMeta(@PathVariable int id) throws ImageNotFoundException { return service.getImageMeta(id); }
}
