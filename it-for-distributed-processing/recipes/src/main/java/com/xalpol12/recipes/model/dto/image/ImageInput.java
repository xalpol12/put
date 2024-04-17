package com.xalpol12.recipes.model.dto.image;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ImageInput {
    @Nullable
    @NotBlank
    @Length(max = 80)
    String name;
}