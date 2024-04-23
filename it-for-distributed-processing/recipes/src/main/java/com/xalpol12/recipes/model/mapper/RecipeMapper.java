package com.xalpol12.recipes.model.mapper;

import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.RecipeCollection;
import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutputShort;
import com.xalpol12.recipes.repository.ImageRepository;
import com.xalpol12.recipes.repository.RecipeCollectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RecipeMapper {

    @Autowired
    protected ImageRepository imageRepository;
    @Autowired
    protected RecipeCollectionRepository recipeCollectionRepository;


    public Recipe inputToRecipe(RecipeInput input) {
        Recipe recipe = Recipe.builder()
                .recipeName(input.getRecipeName())
                .estimatedTime(input.getEstimatedTime())
                .ingredients(input.getIngredients())
                .descriptions(input.getDescriptions())
                .build();
        List<Image> images;
        List<RecipeCollection> collections;
        if (input.getImages() != null) {
             images = stringToImage(input);
            if (!images.isEmpty()) {
                recipe.setImages(images);
            }
        }
        if (input.getRecipeCollections() != null) {
            collections = longToRecipeCollection(input);
            if (!collections.isEmpty()) {
                recipe.setCollections(collections);
            }
        }
        return recipe;
    };

    public List<Image> stringToImage(RecipeInput value) {
        List<Image> images = new ArrayList<>();
        for (String id: value.getImages()) {
            images.add(imageRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Could not find any image with given id")));
        }
        return images;
    }

    public List<RecipeCollection> longToRecipeCollection(RecipeInput value) {
        List<RecipeCollection> collections = new ArrayList<>();
        for (Long id: value.getRecipeCollections()) {
            collections.add(recipeCollectionRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Could not find any list with given id")));
        }
        return collections;
    }


    @Mapping(target = "recipeId", source = "recipe.id")
    public abstract RecipeOutput recipeToOutput(Recipe recipe);
    public abstract List<RecipeOutput> recipeToOutput(List<Recipe> recipe);

    public abstract RecipeOutputShort recipeToOutputShort(Recipe recipe);
    public abstract List<RecipeOutputShort> recipeToOutputShort(List<Recipe> recipes);
}
