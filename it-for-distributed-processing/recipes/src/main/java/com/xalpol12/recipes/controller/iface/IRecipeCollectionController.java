package com.xalpol12.recipes.controller.iface;

import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping(IRecipeCollectionController.RecipeCollectionPath.ROOT)
public interface IRecipeCollectionController {
    class RecipeCollectionPath {
        public static final String ROOT = "/api/recipe/collection";
        private RecipeCollectionPath() {}
    }

    @GetMapping(RecipeCollectionPath.ROOT + "/{uuid}")
    ResponseEntity<RecipeCollectionOutput> getRecipeCollection(@PathVariable("uuid") String uuid);

    @GetMapping
    ResponseEntity<List<RecipeCollectionOutput>> getAllRecipeCollections();

    @PostMapping
    ResponseEntity<URI> addRecipeCollection(RecipeCollectionInput recipeCollectionInput);

    @DeleteMapping("/{uuid}")
    ResponseEntity<Void> deleteRecipeCollection(@PathVariable("uuid") String uuid);

    @DeleteMapping
    ResponseEntity<Void> deleteAllRecipeCollections();

    @PutMapping("/{uuid}")
    ResponseEntity<Void> updateRecipeCollection(
            @PathVariable("uuid") String uuid,
            RecipeCollectionInput recipeCollectionInput);

    //TODO: add real true PATCH
}
