package com.xalpol12.recipes.model.dto.image;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ImageOutputOnlyId {
    private String imageId;

    public ImageOutputOnlyId(String imageId) {
        this.imageId = imageId;
    }
}
