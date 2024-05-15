package com.xalpol12.recipes.model.dto.recipecollection;

import com.xalpol12.recipes.model.dto.recipe.RecipeOutputShort;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCollectionOutput {
    private Long id;

    private String collectionName;

    @ElementCollection
    private List<RecipeOutputShort> recipes;

    private Integer version;
}
