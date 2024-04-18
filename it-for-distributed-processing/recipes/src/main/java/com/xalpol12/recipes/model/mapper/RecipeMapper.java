package com.xalpol12.recipes.model.mapper;

import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutputShort;
import com.xalpol12.recipes.repository.ImageRepository;
import com.xalpol12.recipes.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RecipeMapper {

    @Autowired
    protected ImageRepository repository;

    public abstract Recipe inputToRecipe(RecipeInput input);

    public List<Image> stringToImage(List<String> value) {
        List<Image> images = new ArrayList<>();
        for (String id: value) {
            images.add(repository.getReferenceById(id));
        }
        return images;
    }

    public abstract RecipeOutput recipeToOutput(Recipe recipe);
    public abstract List<RecipeOutput> recipeToOutput(List<Recipe> recipe);

    public abstract RecipeOutputShort recipeToOutputShort(Recipe recipe);
    public abstract List<RecipeOutputShort> recipeToOutputShort(List<Recipe> recipes);
}
