package com.xalpol12.recipes.controller.iface;

import com.github.fge.jsonpatch.JsonPatch;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionMergeRequest;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

public interface IRecipeCollectionController {
    class RecipeCollectionPath {
        public static final String ROOT = "/api/recipe-lists";
        private RecipeCollectionPath() {}
    }

    @GetMapping(RecipeCollectionPath.ROOT)
    ResponseEntity<List<RecipeCollectionOutput>> getAllRecipeCollections();

    @PostMapping(RecipeCollectionPath.ROOT)
    ResponseEntity<URI> addRecipeCollection(RecipeCollectionInput recipeCollectionInput);

    @GetMapping(RecipeCollectionPath.ROOT + "/{id}")
    ResponseEntity<RecipeCollectionOutput> getRecipeCollection(@PathVariable("id") Long id);

    @DeleteMapping(RecipeCollectionPath.ROOT + "/{id}")
    ResponseEntity<Void> deleteRecipeCollection(@PathVariable("id") Long id);

    @PutMapping(RecipeCollectionPath.ROOT + "/{id}")
    ResponseEntity<Void> updateRecipeCollection(
            @PathVariable("id") Long id,
            RecipeCollectionInput recipeCollectionInput);

    @PatchMapping(RecipeCollectionPath.ROOT + "/{id}")
    ResponseEntity<RecipeCollectionOutput> updateRecipeCollection(@PathVariable("id") Long id,
                                                                  @RequestBody JsonPatch patch);

    @PostMapping(RecipeCollectionPath.ROOT + "-merges")
    ResponseEntity<RecipeCollectionOutput> mergeRecipeCollections(
            @RequestBody RecipeCollectionMergeRequest mergeRequest);
}
