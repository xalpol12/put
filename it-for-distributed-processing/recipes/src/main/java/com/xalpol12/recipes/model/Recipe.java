package com.xalpol12.recipes.model;

import com.xalpol12.recipes.model.valueobject.Ingredient;
import com.xalpol12.recipes.model.valueobject.TextParagraph;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Entity(name = "recipes")
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @Column(name = "recipe_id")
    private Long id;

    private int executionTime; //TODO: Find better name

    @ElementCollection
    private List<Ingredient> ingredients;

    @ElementCollection
    private List<TextParagraph> descriptions;

    @OneToMany
    private List<Image> images;

    @ManyToMany(mappedBy = "recipes")
    List<RecipeCollection> collections;
}
