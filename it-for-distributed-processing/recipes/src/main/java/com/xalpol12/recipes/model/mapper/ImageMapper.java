package com.xalpol12.recipes.model.mapper;

import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.image.ImageOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring", uses = Image.class)
public interface ImageMapper {

    @Mapping(target = "name", source = "imageInput.name")
    @Mapping(target = "data", source = "file")
    @Mapping(target = "type", source = "file", qualifiedByName = "mapType")
    Image inputToImage(ImageInput imageInput, MultipartFile file);

    default String mapImageInputToName(ImageInput imageInput) {
        return imageInput.getName();
    }

    default byte[] mapFileToBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            return null;
        }
    }

    @Named("mapType")
    default String mapType(MultipartFile file) {
        return file.getContentType();
    }

    @Mapping(target = "imageId", source = "imageEntity.id")
    @Mapping(target = "lastModifiedAt", source = "imageEntity.lastModifiedAt")
    @Mapping(target = "size", source = "imageEntity", qualifiedByName = "mapDataLength")
    @Mapping(target = "recipes", source = "imageEntity.recipe.id")
    ImageOutput imageToOutput(Image imageEntity);

    @Named("mapDataLength")
    default int mapDataLength(Image imageEntity) {
        return imageEntity.getData().length;
    }

    List<ImageOutput> imageToOutput(List<Image> images);
}
