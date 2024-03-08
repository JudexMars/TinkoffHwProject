package org.judexmars.tinkoffhwproject.mapper;

import org.judexmars.tinkoffhwproject.dto.image.ImageDto;
import org.judexmars.tinkoffhwproject.model.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(target = "fileId", source = "link")
    ImageDto imageToImageDto(ImageEntity image);

    @Mapping(target = "link", source = "fileId")
    ImageEntity imageDtoToImage(ImageDto image);
}
