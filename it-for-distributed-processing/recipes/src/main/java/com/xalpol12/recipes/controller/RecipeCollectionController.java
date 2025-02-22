package com.xalpol12.recipes.controller;

import com.xalpol12.recipes.controller.iface.IRecipeCollectionController;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionInput;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionMergeRequest;
import com.xalpol12.recipes.model.dto.recipecollection.RecipeCollectionOutput;
import com.xalpol12.recipes.service.RecipeCollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecipeCollectionController implements IRecipeCollectionController {
    private final RecipeCollectionService service;

    @Override
    public ResponseEntity<Page<RecipeCollectionOutput>> getAllRecipeCollections(Pageable pageable) {
        return ResponseEntity.ok(service.getAllRecipeCollections(pageable));
    }

    @Override
    public ResponseEntity<RecipeCollectionOutput> addRecipeCollection(RecipeCollectionInput recipeCollectionInput) {
        RecipeCollectionOutput output = service.addRecipeCollection(recipeCollectionInput);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RecipeCollectionPath.ROOT + "/")
                .path(String.valueOf(output.getId()))
                .build()
                .toUri();

        return ResponseEntity.created(uri)
                .body(output);
    }

    @Override
    public ResponseEntity<RecipeCollectionOutput> getRecipeCollection(Long id, int page, int size) {
        return ResponseEntity.ok(service.getRecipeCollection(id, page, size));
    }

    @Override
    public ResponseEntity<Void> deleteRecipeCollection(Long id) {
        service.deleteRecipeCollection(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<RecipeCollectionOutput> updateRecipeCollection(Long id, RecipeCollectionInput recipeCollectionInput, String ifMatchHeader) {
        Integer version = Integer.parseInt(ifMatchHeader);
        RecipeCollectionOutput output = service.updateRecipeCollection(id, recipeCollectionInput, version);
        return ResponseEntity.ok(output);
    }

    @Override
    public ResponseEntity<RecipeCollectionOutput> patchRecipeCollection(Long id, RecipeCollectionInput recipeCollectionInput) {
        RecipeCollectionOutput output = service.patchRecipeCollection(id, recipeCollectionInput);
        return ResponseEntity.ok(output);
    }

    @Override
    public ResponseEntity<RecipeCollectionOutput> mergeRecipeCollections(RecipeCollectionMergeRequest mergeRequest) {
        RecipeCollectionOutput output = service.mergeRecipeCollections(mergeRequest);
        return ResponseEntity.ok(output);
    }
}
