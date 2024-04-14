package com.xalpol12.recipes.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.xalpol12.recipes.controller.iface.IRecipeController;
import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import com.xalpol12.recipes.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RecipeController implements IRecipeController {
    private final RecipeService service;

    @Override
    public ResponseEntity<List<RecipeOutput>> getAllRecipes() {
        return null;
    }

    @Override
    public ResponseEntity<URI> addRecipe(RecipeInput recipeInput) {
        return null;
    }

    @Override
    public ResponseEntity<RecipeOutput> getRecipe(String uuid) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteRecipe(String uuid) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateRecipe(String uuid, ImageInput imageInput) {
        return null;
    }

    @Override
    public ResponseEntity<RecipeOutput> patchRecipe(String uuid, JsonPatch patch) {
        return null;
    }
}
