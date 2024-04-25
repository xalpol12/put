package com.xalpol12.recipes.model.mapper;

import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.RecipeCollection;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutputShort;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import com.xalpol12.recipes.repository.RecipeCollectionRepository;
import com.xalpol12.recipes.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RecipeCollectionMapper {
    private final RecipeCollectionRepository repository;
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    public RecipeCollection inputToCollection(RecipeCollectionInput input) {
        List<Recipe> recipes = new ArrayList<>();
        for (Long id : input.getRecipeIds()) {
            if (recipeRepository.existsById(id)) {
                Recipe recipe = recipeRepository.getReferenceById(id);
                recipes.add(recipe);
            } else {
                throw new EntityNotFoundException("Recipe with id: " + id + " not found");
            }
        }
        return  RecipeCollection.builder()
                .collectionName(input.getCollectionName())
                .recipes(recipes)
                .build();
    }

    public RecipeCollectionOutput collectionToOutput(RecipeCollection collection) {
        List<RecipeOutputShort> recipes = recipeMapper.recipeToOutputShort(collection.getRecipes());
        return RecipeCollectionOutput.builder()
                .id(collection.getId())
                .collectionName(collection.getCollectionName())
                .recipes(recipes)
                .build();
    }
}
