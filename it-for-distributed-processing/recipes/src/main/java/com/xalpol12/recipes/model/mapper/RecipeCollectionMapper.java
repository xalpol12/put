package com.xalpol12.recipes.model.mapper;

import com.xalpol12.recipes.model.RecipeCollection;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeCollectionMapper {
    RecipeCollection inputToCollection(RecipeCollectionInput input);
    RecipeCollectionOutput collectionToOutput(RecipeCollection recipeCollection);
}
