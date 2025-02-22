package com.xalpol12.recipes.controller.iface;

import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IRecipeController {
    class RecipePath {
        public static final String ROOT = "/api/v1/recipes";
        private RecipePath() {}
    }

    @GetMapping(RecipePath.ROOT)
    ResponseEntity<Page<RecipeOutput>> getAllRecipes(Pageable pageable);

    @PostMapping(RecipePath.ROOT)
    ResponseEntity<Void> addRecipeExactlyOnce();

    @GetMapping(RecipePath.ROOT + "/{uuid}")
    ResponseEntity<RecipeOutput> getRecipe(@PathVariable("uuid") Long id);

    @DeleteMapping(RecipePath.ROOT + "/{uuid}")
    ResponseEntity<Void> deleteRecipe(@PathVariable("uuid") Long id);

    @PutMapping(RecipePath.ROOT + "/{uuid}")
    ResponseEntity<RecipeOutput> updateRecipe(@PathVariable("uuid") Long id,
                                              @RequestBody RecipeInput recipeInput,
                                              @RequestHeader("If-Match") String ifMatchHeader);

    @PatchMapping(RecipePath.ROOT + "/{uuid}")
    ResponseEntity<RecipeOutput> patchRecipe(@PathVariable("uuid") Long id,
                                              @RequestBody RecipeInput recipeInput);
}
