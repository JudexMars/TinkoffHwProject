package org.judexmars.tinkoffhwproject.service;

import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.dto.image.ImageDto;
import org.judexmars.tinkoffhwproject.dto.operation.OperationDto;
import org.judexmars.tinkoffhwproject.exception.ImageNotFoundException;
import org.judexmars.tinkoffhwproject.exception.UploadFailedException;
import org.judexmars.tinkoffhwproject.mapper.ImageMapper;
import org.judexmars.tinkoffhwproject.model.ImageEntity;
import org.judexmars.tinkoffhwproject.model.OperationEntity;
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

    /**
     * Check if all ids from the list are present in image table
     * @param imageIds ids
     * @return true / false
     */
    public boolean existAll(List<Long> imageIds) {
        return imageRepository.existsImageByIdIn(imageIds);
    }

    /**
     * Get all images with provided ids
     * @param imageIds ids
     * @return list of all corresponding images
     */
    public List<ImageEntity> getAllImages(List<Long> imageIds) {
        return imageRepository.findAllByIdIn(imageIds);
    }

    /**
     * Get meta information of the image with provided id
     * @param id specified id
     * @return {@link ImageDto} representation of the image
     * @throws ImageNotFoundException if there's no image with this id
     */
    @Cacheable(value = "ImageService::getImageMeta", key = "#id")
    public ImageDto getImageMeta(long id) throws ImageNotFoundException {
        Optional<ImageEntity> imageOptional = imageRepository.findById(id);
        if (imageOptional.isEmpty()) {
            throw new ImageNotFoundException(id);
        }
        var image = mapper.imageToImageDto(imageOptional.get());

        operationService.logOperation(
                new OperationDto(
                        String.format("Read image metadata: %s", image),
                        LocalDateTime.now(),
                        OperationEntity.OperationType.WRITE
                )
        );

        return image;
    }

    /**
     * Returns byte array of image file
     * @param link link of the desired image
     * @return binary file
     * @throws Exception if image is not found or can't be downloaded for some reason
     */
    public byte[] downloadImage(String link) throws Exception {
        if (!imageRepository.existsImageByLink(link)) {
            throw new ImageNotFoundException(link);
        }

        return minioService.downloadImage(link);
    }

    /**
     * Upload new image
     * @param file binary file of image
     * @return meta information of this image as {@link ImageDto}
     * @throws UploadFailedException if the image cannot be uploaded
     */
    @Cacheable(value = "ImageService::getImageMeta", key = "#file.originalFilename")
    public ImageDto uploadImage(MultipartFile file) throws UploadFailedException {
        try {
            var image = minioService.uploadImage(file);
            imageRepository.save(mapper.imageDtoToImage(image));
            operationService.logOperation(
                    new OperationDto(String.format("Upload image: %s", image),
                            LocalDateTime.now(),
                            OperationEntity.OperationType.WRITE)
            );
            return image;
        } catch (Exception ex) {
            throw new UploadFailedException();
        }
    }
}
