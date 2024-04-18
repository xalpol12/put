package com.xalpol12.recipes.model.dto.recipe;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class RecipeOutputShort {
    private Long recipeId;
    private int estimatedTime;
}
