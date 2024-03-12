package com.xalpol12.recipes.model.mapper;

import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.image.ImageOutput;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageMapper {


    public Image mapToImageEntity(ImageInput imageInput, MultipartFile file) {
        return new Image();
    }

    public ImageOutput mapToImageOutput(Image imageEntity) {
        return new ImageOutput();
    }
}
