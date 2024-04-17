package com.xalpol12.recipes.model.mapper;

import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    Recipe inputToRecipe(RecipeInput input);

    RecipeOutput recipeToOutput(Recipe recipe);

    List<RecipeOutput> recipeToOutput(List<Recipe> recipe);
}
