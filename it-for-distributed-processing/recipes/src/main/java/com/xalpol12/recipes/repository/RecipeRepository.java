package com.xalpol12.recipes.repository;

import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.valueobject.Ingredient;
import com.xalpol12.recipes.model.valueobject.TextParagraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Modifying
    @Query("update Recipe r set r.estimatedTime = ?1, r.ingredients = ?2, r.descriptions = ?3, r.images = ?4 where r.id = ?5")
    void updateRecipe(int estimatedTime,
                      List<Ingredient> ingredients,
                      List<TextParagraph> descriptions,
                      List<Image> images,
                      Long id);
}