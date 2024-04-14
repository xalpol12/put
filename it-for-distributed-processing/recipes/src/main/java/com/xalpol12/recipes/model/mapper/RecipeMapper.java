package com.xalpol12.recipes.model.mapper;

import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {

    public Recipe mapToRecipe(RecipeInput input) {
        throw new NotImplementedException();
    }

    public RecipeOutput mapToRecipeOutput(Recipe recipe) {
        throw new NotImplementedException();
    }


}
