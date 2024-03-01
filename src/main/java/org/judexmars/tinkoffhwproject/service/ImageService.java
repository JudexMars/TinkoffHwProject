package org.judexmars.tinkoffhwproject.service;

import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.ImageDto;
import org.judexmars.tinkoffhwproject.dto.OperationDto;
import org.judexmars.tinkoffhwproject.exception.ImageNotFoundException;
import org.judexmars.tinkoffhwproject.mapper.ImageMapper;
import org.judexmars.tinkoffhwproject.model.Image;
import org.judexmars.tinkoffhwproject.model.Operation;
import org.judexmars.tinkoffhwproject.repository.ImageRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    private final ImageMapper mapper;

    private final OperationService operationService;

    private final MinioService minioService;

    public boolean existAll(List<Long> imageIds) {
        return imageRepository.existsImageByIdIn(imageIds);
    }

    public List<Image> getAllImages(List<Long> imageIds) {
        return imageRepository.findAllByIdIn(imageIds);
    }

    @Cacheable(value = "ImageService::getImageMeta", key = "#id")
    public ImageDto getImageMeta(long id) throws ImageNotFoundException {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isEmpty()) {
            throw new ImageNotFoundException(id);
        }
        var image = mapper.imageToImageDto(imageOptional.get());

        operationService.logOperation(
                new OperationDto(
                        String.format("Read image metadata: %s", image),
                        LocalDateTime.now(),
                        Operation.OperationType.WRITE
                )
        );

        return image;
    }

    public byte[] downloadImage(String link) throws Exception {
        if (imageRepository.existsImageByLink(link)) {
            throw new ImageNotFoundException(link);
        }

        return minioService.downloadImage(link);
    }

    @Cacheable(value = "ImageService::getImageMeta", key = "#file.originalFilename")
    public ImageDto uploadImage(MultipartFile file) throws Exception {
        var image = minioService.uploadImage(file);
        imageRepository.save(mapper.imageDtoToImage(image));
        operationService.logOperation(
                new OperationDto(String.format("Upload image: %s", image),
                        LocalDateTime.now(),
                        Operation.OperationType.WRITE)
        );
        return image;
    }
}
