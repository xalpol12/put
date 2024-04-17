package com.xalpol12.recipes.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.xalpol12.recipes.controller.iface.IRecipeController;
import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import com.xalpol12.recipes.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecipeController implements IRecipeController {
    private final RecipeService service;

    @Override
    public ResponseEntity<Page<RecipeOutput>> getAllRecipes(Pageable pageable) {
        return ResponseEntity.ok(service.getAllRecipes(pageable));
    }

    @Override
    public ResponseEntity<RecipeOutput> addRecipe(RecipeInput recipeInput) {
        RecipeOutput output = service.addRecipe(recipeInput);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RecipePath.ROOT)
                .path(String.valueOf(output.getRecipeId()))
                .build()
                .toUri();

        return ResponseEntity.created(uri)
                .body(output);
    }

    @Override
    public ResponseEntity<RecipeOutput> getRecipe(Long id) {
        return ResponseEntity.ok(service.getRecipe(id));
    }

    @Override
    public ResponseEntity<Void> deleteRecipe(Long id) {
        service.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<RecipeOutput> updateRecipe(Long id, RecipeInput recipeInput) {
        RecipeOutput output = service.updateRecipe(id, recipeInput);
        return ResponseEntity.ok(output);
    }

    @Override
    public ResponseEntity<RecipeOutput> patchRecipe(Long id, JsonPatch patch) {
        return null;
    }
}
