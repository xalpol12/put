package com.xalpol12.recipes.model;

import jakarta.persistence.*;

import java.util.List;
@Entity(name = "recipe_collections")
public class RecipeCollection {
    @Id
    private Long id;

    @ManyToMany(mappedBy = "recipes")
    List<Recipe> recipes;
}
