package com.xalpol12.recipes.model.dto.recipe;

import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.RecipeCollection;
import com.xalpol12.recipes.model.dto.image.ImageOutput;
import com.xalpol12.recipes.model.dto.image.ImageOutputOnlyId;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutputOnlyId;
import com.xalpol12.recipes.model.valueobject.Ingredient;
import com.xalpol12.recipes.model.valueobject.TextParagraph;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Data
@Builder
public class RecipeOutput {
    private Long recipeId;

    private String recipeName;

    private int estimatedTime;

    @ElementCollection
    private List<Ingredient> ingredients;

    @ElementCollection
    private List<TextParagraph> descriptions;

    private List<ImageOutputOnlyId> images;

    private List<RecipeCollectionOutputOnlyId> collections;

    private Integer version;
}
