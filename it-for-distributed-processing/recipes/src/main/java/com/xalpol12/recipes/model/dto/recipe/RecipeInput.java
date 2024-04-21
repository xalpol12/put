package com.xalpol12.recipes.model.dto.recipe;

import com.xalpol12.recipes.model.valueobject.Ingredient;
import com.xalpol12.recipes.model.valueobject.TextParagraph;
import lombok.Data;

import java.util.List;

@Data
public class RecipeInput {
    private String recipeName;
    private int estimatedTime;
    private List<Ingredient> ingredients;
    private List<TextParagraph> descriptions;
    private List<String> images; // how to create association with Images?
    private List<Long> recipeCollections;
}
