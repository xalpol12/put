package com.xalpol12.recipes.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.xalpol12.recipes.controller.iface.IRecipeCollectionController;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionMergeRequest;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import com.xalpol12.recipes.service.RecipeCollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecipeCollectionController implements IRecipeCollectionController {
    private final RecipeCollectionService service;

    @Override
    public ResponseEntity<List<RecipeCollectionOutput>> getAllRecipeCollections() {
        return null;
    }

    @Override
    public ResponseEntity<URI> addRecipeCollection(RecipeCollectionInput recipeCollectionInput) {
        return null;
    }

    @Override
    public ResponseEntity<RecipeCollectionOutput> getRecipeCollection(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteRecipeCollection(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateRecipeCollection(Long id, RecipeCollectionInput recipeCollectionInput) {
        return null;
    }

    @Override
    public ResponseEntity<RecipeCollectionOutput> updateRecipeCollection(Long id, JsonPatch patch) {
        return null;
    }

    @Override
    public ResponseEntity<RecipeCollectionOutput> mergeRecipeCollections(RecipeCollectionMergeRequest mergeRequest) {
        return null;
    }
}
