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
        public static final String ROOT = "/api/lists";
        private RecipeCollectionPath() {}
    }

    @GetMapping(RecipeCollectionPath.ROOT + "/{id}")
    ResponseEntity<RecipeCollectionOutput> getRecipeCollection(@PathVariable("id") Long id);

    @GetMapping
    ResponseEntity<List<RecipeCollectionOutput>> getAllRecipeCollections();

    @PostMapping
    ResponseEntity<URI> addRecipeCollection(RecipeCollectionInput recipeCollectionInput);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteRecipeCollection(@PathVariable("id") Long id);

    @DeleteMapping
    ResponseEntity<Void> deleteAllRecipeCollections();

    @PutMapping("/{id}")
    ResponseEntity<Void> updateRecipeCollection(
            @PathVariable("id") Long id,
            RecipeCollectionInput recipeCollectionInput);

    //TODO: add real true PATCH
}
