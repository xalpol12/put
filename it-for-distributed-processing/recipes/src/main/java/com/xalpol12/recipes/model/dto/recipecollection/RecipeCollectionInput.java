package com.xalpol12.recipes.model.dto.recipecollection;

import lombok.Data;

import java.util.List;

@Data
public class RecipeCollectionInput {
    private String collectionName;
    private List<String> recipeIds;
}
