package com.xalpol12.recipes.controller;

import com.xalpol12.recipes.controller.iface.IRecipeCollectionController;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
public class RecipeCollectionController implements IRecipeCollectionController {
    @Override
    public ResponseEntity<RecipeCollectionOutput> getRecipeCollection(String uuid) {
        return null;
    }

    @Override
    public ResponseEntity<List<RecipeCollectionOutput>> getAllRecipeCollections() {
        return null;
    }

    @Override
    public ResponseEntity<URI> addRecipeCollection(RecipeCollectionInput recipeCollectionInput) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteRecipeCollection(String uuid) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteAllRecipeCollections() {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateRecipeCollection(String uuid, RecipeCollectionInput recipeCollectionInput) {
        return null;
    }
}
