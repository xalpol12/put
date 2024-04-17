package com.xalpol12.recipes.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import com.xalpol12.recipes.model.mapper.RecipeMapper;
import com.xalpol12.recipes.repository.ImageRepository;
import com.xalpol12.recipes.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository repository;
    private final ImageRepository imageRepository;
    private final RecipeMapper mapper;

    public Page<RecipeOutput> getAllRecipes(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::recipeToOutput);
    }

    public RecipeOutput addRecipe(RecipeInput recipeInput) {
        Recipe recipe = mapper.inputToRecipe(recipeInput);
        Recipe saved = repository.save(recipe);
        return mapper.recipeToOutput(saved);
    }

    public RecipeOutput getRecipe(Long id) {
        Recipe recipe = getRecipeOrThrow(id);
        return mapper.recipeToOutput(recipe);
    }

    public void deleteRecipe(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public RecipeOutput updateRecipe(Long id, RecipeInput recipeInput) {
        Recipe recipe = getRecipeOrThrow(id);

        recipe.setEstimatedTime(recipeInput.getEstimatedTime());
        recipe.setIngredients(recipeInput.getIngredients());
        recipe.setDescriptions(recipeInput.getDescriptions());

        List<Image> images = new ArrayList<>();
        for (String imageId : recipeInput.getImages()) {
            Image image = imageRepository.getReferenceById(imageId);
            images.add(image);
        }

        recipe.setImages(images);
        repository.save(recipe);

        return mapper.recipeToOutput(recipe);
    }

    @Transactional
    public RecipeOutput patchRecipe(Long id, JsonPatch patch) {
        Recipe recipe = getRecipeOrThrow(id);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode patched = patch.apply(objectMapper.convertValue(recipe, JsonNode.class));
            Recipe patchedRecipe = objectMapper.treeToValue(patched, Recipe.class);

            if (patchedRecipe.getEstimatedTime() != null) {
                recipe.setEstimatedTime(patchedRecipe.getEstimatedTime());
            }
            if (patchedRecipe.getIngredients() != null) {
                recipe.setIngredients(patchedRecipe.getIngredients());
            }
            if (patchedRecipe.getDescriptions() != null) {
                recipe.setDescriptions(patchedRecipe.getDescriptions());
            }
            if (patchedRecipe.getImages() != null) {
                recipe.setImages(patchedRecipe.getImages());
            }

            Recipe updatedRecipe = repository.save(recipe);

            return mapper.recipeToOutput(updatedRecipe);
        } catch (Exception e) { //TODO: add exception handling
            throw new NotImplementedException("Implement this exception! RecipeService::patchRecipe");
        }
    }

    private Recipe getRecipeOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe with id: " + id + " does not exist"));
    }
}
