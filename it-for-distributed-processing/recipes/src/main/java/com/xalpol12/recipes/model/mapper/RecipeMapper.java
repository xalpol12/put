package com.xalpol12.recipes.model.mapper;

import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.RecipeCollection;
import com.xalpol12.recipes.model.dto.image.ImageOutputOnlyId;
import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutputShort;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutputOnlyId;
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
    }

    protected List<Image> stringToImage(RecipeInput value) {
        List<Image> images = new ArrayList<>();
        for (String id: value.getImages()) {
            images.add(imageRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Could not find any image with given id")));
        }
        return images;
    }

    protected List<RecipeCollection> longToRecipeCollection(RecipeInput value) {
        List<RecipeCollection> collections = new ArrayList<>();
        for (Long id: value.getRecipeCollections()) {
            collections.add(recipeCollectionRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Could not find any list with given id")));
        }
        return collections;
    }


    public RecipeOutput recipeToOutput(Recipe recipe) {
        List<ImageOutputOnlyId> images = new ArrayList<>();
        List<RecipeCollectionOutputOnlyId> collections = new ArrayList<>();
        if (recipe.getImages() != null) {
            images = recipe.getImages()
                    .stream()
                    .map(image -> new ImageOutputOnlyId(image.getId()))
                    .toList();
        }
        if (recipe.getCollections() != null) {
            collections = recipe.getCollections()
                    .stream()
                    .map(collection -> new RecipeCollectionOutputOnlyId(collection.getId()))
                    .toList();
        }

        return RecipeOutput.builder()
                .recipeId(recipe.getId())
                .recipeName(recipe.getRecipeName())
                .estimatedTime(recipe.getEstimatedTime())
                .ingredients(recipe.getIngredients())
                .descriptions(recipe.getDescriptions())
                .images(images)
                .collections(collections)
                .build();
    }

    public abstract List<RecipeOutput> recipeToOutput(List<Recipe> recipe);

    @Mapping(target = "recipeId", source = "recipe.id")
    public abstract RecipeOutputShort recipeToOutputShort(Recipe recipe);

    public abstract List<RecipeOutputShort> recipeToOutputShort(List<Recipe> recipes);
}
