package com.xalpol12.recipes.service;

import com.xalpol12.recipes.exception.custom.IncompleteUpdateFormException;
import com.xalpol12.recipes.model.Image;
import com.xalpol12.recipes.model.Recipe;
import com.xalpol12.recipes.model.RecipeCollection;
import com.xalpol12.recipes.model.dto.recipe.RecipeInput;
import com.xalpol12.recipes.model.dto.recipe.RecipeOutput;
import com.xalpol12.recipes.model.mapper.RecipeMapper;
import com.xalpol12.recipes.repository.ImageRepository;
import com.xalpol12.recipes.repository.RecipeCollectionRepository;
import com.xalpol12.recipes.repository.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeCollectionRepository recipeCollectionRepository;
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

    public Long createRecipeToken() {
        Recipe defaultRecipe = new Recipe();
        defaultRecipe.setRecipeName("default");
        defaultRecipe.setVersion(0);
        defaultRecipe.setEstimatedTime(0);
        Recipe saved = repository.save(defaultRecipe);
        return saved.getId();
    }

    public RecipeOutput getRecipe(Long id) {
        Recipe recipe = getRecipeOrThrow(id);
        return mapper.recipeToOutput(recipe);
    }

    public void deleteRecipe(Long id) {
        Recipe recipe = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe with id: " + id + " does not exist"));
        for (RecipeCollection collection : recipe.getCollections()) {
            collection.getRecipes().remove(recipe);
        }
        repository.delete(recipe);
    }

    @Transactional
    public RecipeOutput updateRecipe(Long id, RecipeInput recipeInput, Integer version) {
        Recipe recipe = getRecipeOrThrow(id);

        if (!Objects.equals(version, recipe.getVersion())) {
            throw new OptimisticLockException();
        }

        try {
            recipe.setRecipeName(recipeInput.getRecipeName());
            recipe.setEstimatedTime(recipeInput.getEstimatedTime());
            recipe.setIngredients(recipeInput.getIngredients());
            recipe.setDescriptions(recipeInput.getDescriptions());
            recipe.setImages(getAllImageReferences(recipeInput.getImages()));
            recipe.setCollections(getAllRecipeCollectionReferences(recipeInput.getRecipeCollections()));

            recipe.setVersion(recipe.getVersion() + 1);

            repository.save(recipe);
            return mapper.recipeToOutput(recipe);

        } catch (NullPointerException e) {
            throw new IncompleteUpdateFormException("Update form does not include all the entity's fields");
        }
    }

    private List<Image> getAllImageReferences(List<String> uuids) {
        List<Image> images = new ArrayList<>();
        for (String imageId : uuids) {
            Image image = imageRepository
                    .findById(imageId)
                    .orElseThrow(() -> new EntityNotFoundException("Could not find image with id: " + imageId));
            images.add(image);
        }
        return images;
    }

    private List<RecipeCollection> getAllRecipeCollectionReferences(List<Long> ids) {
        List<RecipeCollection> collections = new ArrayList<>();
        for (Long collectionId : ids) {
            RecipeCollection collection = recipeCollectionRepository
                    .findById(collectionId)
                    .orElseThrow(() -> new EntityNotFoundException("Could not find collection with id: " + collectionId));
            collections.add(collection);
        }
        return collections;
    }

    @Transactional
    public RecipeOutput patchRecipe(Long id, RecipeInput patch) {
        Recipe recipe = getRecipeOrThrow(id);

        if (patch.getRecipeName() != null) {
            recipe.setRecipeName(patch.getRecipeName());
        }
        if (patch.getEstimatedTime() != null) {
            recipe.setEstimatedTime(patch.getEstimatedTime());
        }
        if (patch.getIngredients() != null) {
            recipe.setIngredients(patch.getIngredients());
        }
        if (patch.getDescriptions() != null) {
            recipe.setDescriptions(patch.getDescriptions());
        }
        if (patch.getImages() != null) {
            recipe.setImages(getAllImageReferences(patch.getImages()));
        }
        if (patch.getRecipeCollections() != null) {
            recipe.setCollections(getAllRecipeCollectionReferences(patch.getRecipeCollections()));
        }

        return mapper.recipeToOutput(repository.save(recipe));
    }

    private Recipe getRecipeOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe with id: " + id + " does not exist"));
    }
}
