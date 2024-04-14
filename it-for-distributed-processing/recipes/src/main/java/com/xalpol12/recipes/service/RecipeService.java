package com.xalpol12.recipes.service;

import com.github.fge.jsonpatch.JsonPatch;
import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.dto.image.ImageInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import com.xalpol12.recipes.model.mapper.RecipeMapper;
import com.xalpol12.recipes.repository.RecipeCollectionRepository;
import com.xalpol12.recipes.repository.RecipeRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository repository;
    private final RecipeMapper mapper;

    //TODO: implement getting next page
    public List<RecipeOutput> getAllRecipes() {
        return repository
                .findAll(Pageable.ofSize(10))
                .stream()
                .map(mapper::mapToRecipeOutput)
                .toList();
    }

    public URI addRecipe(RecipeInput recipeInput) {
        return null;
    }

    public RecipeOutput getRecipe(Long id) {
        Recipe recipe = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Could not find entity with given id"));
        return mapper.mapToRecipeOutput(recipe);
    }

    public void deleteRecipe(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public RecipeOutput updateRecipe(Long id, RecipeInput recipeInput) {
        repository.updateRecipe(
                recipeInput.getEstimatedTime(),
                recipeInput.getIngredients(),
                recipeInput.getDescriptions(),
                recipeInput.getImages(),
                id);
        return null;
    }

    @Transactional
    public RecipeOutput patchRecipe(Long id, JsonPatch patch) {
            return null;
    }
}
