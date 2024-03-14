package com.xalpol12.recipes.controller;

import com.xalpol12.recipes.controller.iface.IRecipeCollectionController;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
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
    public ResponseEntity<RecipeCollectionOutput> getRecipeCollection(Long id) {
        return ResponseEntity.ok(service.getRecipeCollection(id));
    }

    @Override
    public ResponseEntity<List<RecipeCollectionOutput>> getAllRecipeCollections() {
        return ResponseEntity.ok(service.getAllRecipeCollections());
    }

    @Override
    public ResponseEntity<URI> addRecipeCollection(RecipeCollectionInput recipeCollectionInput) {
        URI uri = service.addRecipeCollection(recipeCollectionInput);
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<Void> deleteRecipeCollection(Long id) {
        service.deleteRecipeCollection(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteAllRecipeCollections() {
        service.deleteAllRecipeCollections();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateRecipeCollection(Long id, RecipeCollectionInput recipeCollectionInput) {
        service.updateRecipeCollection(id, recipeCollectionInput);
        return ResponseEntity.ok().build();
    }
}
