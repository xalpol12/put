package com.xalpol12.recipes.model.mapper;

import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.RecipeCollection;
import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutputShort;
import com.xalpol12.recipes.repository.ImageRepository;
import com.xalpol12.recipes.repository.RecipeCollectionRepository;
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

    @Mapping(target = "images", source = "input")
    @Mapping(target = "collections", source = "input")
    public abstract Recipe inputToRecipe(RecipeInput input);

    public List<Image> stringToImage(RecipeInput value) {
        List<Image> images = new ArrayList<>();
        for (String id: value.getImages()) {
            images.add(imageRepository.getReferenceById(id));
        }
        return images;
    }

    public List<RecipeCollection> longToRecipeCollection(RecipeInput value) {
        List<RecipeCollection> collections = new ArrayList<>();
        for (Long id: value.getRecipeCollections()) {
            collections.add(recipeCollectionRepository.getReferenceById(id));
        }
        return collections;
    }

    public abstract RecipeOutput recipeToOutput(Recipe recipe);
    public abstract List<RecipeOutput> recipeToOutput(List<Recipe> recipe);

    public abstract RecipeOutputShort recipeToOutputShort(Recipe recipe);
    public abstract List<RecipeOutputShort> recipeToOutputShort(List<Recipe> recipes);
}
