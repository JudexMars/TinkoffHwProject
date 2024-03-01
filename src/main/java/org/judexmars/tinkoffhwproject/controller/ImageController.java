package org.judexmars.tinkoffhwproject.controller;

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
public class ImageController {

    private final ImageService service;

    @PostMapping("/load")
    public ImageDto loadImage(MultipartFile file) throws Exception {
        return service.uploadImage(file);
    }

    @GetMapping(value = "/image/{link}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable String link) throws Exception {
        return service.downloadImage(link);
    }

    @GetMapping("/image/{id}/meta")
    public ImageDto getMeta(@PathVariable int id) throws ImageNotFoundException { return service.getImageMeta(id); }
}
