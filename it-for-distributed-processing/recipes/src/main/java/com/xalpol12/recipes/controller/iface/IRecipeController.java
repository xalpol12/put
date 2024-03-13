package com.xalpol12.recipes.controller.iface;

import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping(IRecipeController.RecipePath.ROOT)
public interface IRecipeController {
    class RecipePath {
        public static final String ROOT = "/api/recipes";
        private RecipePath() {}
    }

    @GetMapping( "/{uuid}")
    ResponseEntity<RecipeOutput> getRecipe(@PathVariable("uuid") String uuid);

    @GetMapping
    ResponseEntity<List<RecipeOutput>> getAllRecipes();

    @PostMapping
    ResponseEntity<URI> addRecipe(RecipeInput recipeInput);

    @DeleteMapping("/{uuid}")
    ResponseEntity<Void> deleteRecipe(@PathVariable("uuid") String uuid);

    @DeleteMapping
    ResponseEntity<Void> deleteAllRecipes();

    @PutMapping(RecipePath.ROOT + "/{uuid}")
    ResponseEntity<Void> updateRecipe(@PathVariable("uuid") String uuid, ImageInput imageInput);

    //TODO: add TRUE patch
}
