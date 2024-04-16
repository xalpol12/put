package com.xalpol12.recipes.model.dto.image;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageOutput {

    private String imageId;

    private String name;

    private String url;

    private String type;

    private long size;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;
}
