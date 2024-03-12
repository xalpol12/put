package com.xalpol12.recipes.model.dto.image;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ImageInput {

    @Nullable
    @NotBlank
    @Length(max = 80)
    @Schema(name = "name",
            description = "New name that the Image entity will be updated with." +
                    "Must not be blank",
            example = "image2",
            nullable = true,
            maxLength = 80)
    String name;
}
