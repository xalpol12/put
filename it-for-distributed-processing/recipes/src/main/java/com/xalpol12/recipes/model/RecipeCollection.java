package com.xalpol12.recipes.model;

import jakarta.persistence.*;

import java.util.List;
@Entity(name = "recipe_collections")
public class RecipeCollection {
    @Id
    @Column(name = "recipe_collection_id")
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "recipes",
            joinColumns = @JoinColumn(name = "recipe_collection_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    List<Recipe> recipes;
}
