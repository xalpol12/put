package com.xalpol12.recipes.model;

import com.xalpol12.recipes.model.valueobject.Ingredient;
import com.xalpol12.recipes.model.valueobject.TextParagraph;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

    private int estimatedTime; // in minutes

    @ElementCollection
    private List<Ingredient> ingredients;

    @ElementCollection
    private List<TextParagraph> descriptions;

    @OneToMany(mappedBy = "recipe")
    @Cascade({CascadeType.REMOVE})
    private List<Image> images;

    // define as
    @ManyToMany(mappedBy = "recipes")
    List<RecipeCollection> collections;
}
