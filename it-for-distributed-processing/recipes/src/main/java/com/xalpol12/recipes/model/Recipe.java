package com.xalpol12.recipes.model;

import com.xalpol12.recipes.model.valueobject.TextParagraph;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "recipes")
public class Recipe {
    @Id
    private Long id;

    private int executionTime; //TODO: Find better name

    private List<Ingredient> ingredients;

    private List<TextParagraph> descriptions;

    @ManyToMany(mappedBy = "images")
    private List<Image> images;

    @ManyToMany(mappedBy = "recipe_collections")
    List<RecipeCollection> collections;
}
