package com.xalpol12.recipes.model.mapper;

import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.RecipeCollection;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutputShort;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import com.xalpol12.recipes.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RecipeCollectionMapper {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    public RecipeCollection inputToCollection(RecipeCollectionInput input) {
        List<Recipe> recipes = new ArrayList<>();
        for (Long id : input.getRecipeIds()) {
            Recipe recipe = recipeRepository
                    .findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Recipe with id: " + id + " not found"));
            recipes.add(recipe);
        }
        return  RecipeCollection.builder()
                .collectionName(input.getCollectionName())
                .recipes(recipes)
                .build();
    }

    public RecipeCollectionOutput collectionToOutput(RecipeCollection collection, int page, int size) {
        List<RecipeOutputShort> recipes = recipeMapper.recipeToOutputShort(collection.getRecipes());
        return RecipeCollectionOutput.builder()
                .id(collection.getId())
                .collectionName(collection.getCollectionName())
                .recipes(getPaginatedRecipes(recipes, page, size))
                .version(collection.getVersion())
                .page(page)
                .pageSize(size)
                .build();
    }
    private List<RecipeOutputShort> getPaginatedRecipes(List<RecipeOutputShort> recipeOutputShorts, int page, int size) {
        int totalRecipes = recipeOutputShorts.size();
        int totalPages = (int) Math.ceil((double) totalRecipes / size);

        if (page < 0 || page >= totalPages) {
            return Collections.emptyList();
        }

        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalRecipes);
        return recipeOutputShorts.subList(startIndex, endIndex);    }
}
