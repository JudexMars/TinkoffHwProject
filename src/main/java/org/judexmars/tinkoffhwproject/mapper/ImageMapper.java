package org.judexmars.tinkoffhwproject.mapper;

import org.judexmars.tinkoffhwproject.dto.ImageDto;
import org.judexmars.tinkoffhwproject.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(target = "fileId", source = "link")
    ImageDto imageToImageDto(Image image);

    @Mapping(target = "link", source = "fileId")
    Image imageDtoToImage(ImageDto image);
}
