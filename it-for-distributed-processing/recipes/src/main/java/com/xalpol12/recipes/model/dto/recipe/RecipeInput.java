package com.xalpol12.recipes.model.dto.recipe;

import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.valueobject.Ingredient;
import com.xalpol12.recipes.model.valueobject.TextParagraph;
import lombok.Data;

import java.util.List;

@Data
public class RecipeInput {
    private int estimatedTime;
    private List<Ingredient> ingredients;
    private List<TextParagraph> descriptions;
    private List<Image> images; // how to create association with Images?
}
