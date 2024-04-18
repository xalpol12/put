package com.xalpol12.recipes.model.mapper;

import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.RecipeCollection;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutputShort;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import com.xalpol12.recipes.repository.RecipeCollectionRepository;
import com.xalpol12.recipes.repository.RecipeRepository;
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
        for (String id : input.getRecipeIds()) {
            Recipe recipe = recipeRepository.getReferenceById(Long.parseLong(id));
            recipes.add(recipe);
        }
        RecipeCollection collection = RecipeCollection.builder()
                .collectionName(input.getCollectionName())
                .recipes(recipes)
                .build();
        return repository.save(collection);
    }

    public RecipeCollectionOutput collectionToOutput(RecipeCollection collection) {
        List<RecipeOutputShort> recipes = recipeMapper.recipeToOutputShort(collection.getRecipes());
        return RecipeCollectionOutput.builder()
                .collectionName(collection.getCollectionName())
                .recipes(recipes)
                .build();
    }
}
