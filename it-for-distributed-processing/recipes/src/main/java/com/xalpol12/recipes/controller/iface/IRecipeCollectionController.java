package com.xalpol12.recipes.controller.iface;

import com.github.fge.jsonpatch.JsonPatch;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionMergeRequest;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

public interface IRecipeCollectionController {
    class RecipeCollectionPath {
        public static final String ROOT = "/api/v1/recipe-lists";
        private RecipeCollectionPath() {}
    }

    @GetMapping(RecipeCollectionPath.ROOT)
    ResponseEntity<Page<RecipeCollectionOutput>> getAllRecipeCollections(Pageable pageable);

    @PostMapping(RecipeCollectionPath.ROOT)
    ResponseEntity<RecipeCollectionOutput> addRecipeCollection(
            @RequestBody RecipeCollectionInput recipeCollectionInput);

    @GetMapping(RecipeCollectionPath.ROOT + "/{id}")
    ResponseEntity<RecipeCollectionOutput> getRecipeCollection(@PathVariable("id") Long id);

    @DeleteMapping(RecipeCollectionPath.ROOT + "/{id}")
    ResponseEntity<Void> deleteRecipeCollection(@PathVariable("id") Long id);

    @PutMapping(RecipeCollectionPath.ROOT + "/{id}")
    ResponseEntity<RecipeCollectionOutput> updateRecipeCollection(
            @PathVariable("id") Long id,
            @RequestBody RecipeCollectionInput recipeCollectionInput);

    @PatchMapping(RecipeCollectionPath.ROOT + "/{id}")
    ResponseEntity<RecipeCollectionOutput> patchRecipeCollection(@PathVariable("id") Long id,
                                                                 @RequestBody JsonPatch patch);

    @PostMapping(RecipeCollectionPath.ROOT + "-merges")
    ResponseEntity<RecipeCollectionOutput> mergeRecipeCollections(
            @RequestBody RecipeCollectionMergeRequest mergeRequest);
}
