package com.xalpol12.recipes.controller.iface;

import com.github.fge.jsonpatch.JsonPatch;
import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

public interface IRecipeController {
    class RecipePath {
        public static final String ROOT = "/api/recipes";
        private RecipePath() {}
    }

    @GetMapping(RecipePath.ROOT)
    ResponseEntity<List<RecipeOutput>> getAllRecipes();

    @PostMapping(RecipePath.ROOT)
    ResponseEntity<URI> addRecipe(RecipeInput recipeInput);

    @GetMapping(RecipePath.ROOT + "/{uuid}")
    ResponseEntity<RecipeOutput> getRecipe(@PathVariable("uuid") Long id);

    @DeleteMapping(RecipePath.ROOT + "/{uuid}")
    ResponseEntity<Void> deleteRecipe(@PathVariable("uuid") Long id);

    @PutMapping(RecipePath.ROOT + "/{uuid}")
    ResponseEntity<RecipeOutput> updateRecipe(@PathVariable("uuid") Long id, RecipeInput recipeInput);

    @PatchMapping(RecipePath.ROOT +"/{uuid}")
    ResponseEntity<RecipeOutput> patchRecipe(@PathVariable("uuid") Long id, JsonPatch patch);
}
